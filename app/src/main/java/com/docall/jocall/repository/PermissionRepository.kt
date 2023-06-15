package com.docall.jocall.repository

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.telecom.TelecomManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleCoroutineScope
import com.docall.jocall.App
import com.docall.jocall.base.LauncherRegistrator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class PermissionRepository(launcherRegistrator: LauncherRegistrator) {

    val hasCallerPermission: Boolean get() = with(App.instance) {
        getSystemService(TelecomManager::class.java).defaultDialerPackage == packageName
    }
    val hasOverlayPermission: Boolean get() = Settings.canDrawOverlays(App.instance)
    val hasContactsPermission: Boolean
        get() = checkPermission(Manifest.permission.READ_CONTACTS)

    val hasNecessaryPermissions
        get() = hasCallerPermission && hasOverlayPermission && hasContactsPermission

    private var onCallerPermissionResult: ((Boolean) -> Unit)? = null
    private val callerPermissionLauncher = launcherRegistrator
        .registerActivityResultLauncher(ActivityResultContracts.StartActivityForResult()) {
            onCallerPermissionResult?.invoke(it.resultCode == RESULT_OK)
            onCallerPermissionResult = null
        }

    fun askCallerPermission(onResult: (Boolean) -> Unit) =
        if (hasCallerPermission)
            onResult(true)
        else {
            onCallerPermissionResult = onResult
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                App.instance.getSystemService(RoleManager::class.java)
                    .createRequestRoleIntent(RoleManager.ROLE_DIALER)
            else {
                Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                    .putExtra(
                        TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME,
                        App.instance.packageName
                    )
            }.let(callerPermissionLauncher::launch)
        }

    private var onOverlayPermissionResult: ((Boolean) -> Unit)? = null
    private val overlayPermissionLauncher = launcherRegistrator
        .registerActivityResultLauncher(ActivityResultContracts.StartActivityForResult()) {
            onOverlayPermissionResult?.invoke(hasOverlayPermission)
            onOverlayPermissionResult = null
        }

    fun askOverlayPermission(onResult: (Boolean) -> Unit) =
        if (hasOverlayPermission)
            onResult(true)
        else {
            onOverlayPermissionResult = onResult
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                .setData(Uri.fromParts("package", App.instance.packageName, null))
                .let(overlayPermissionLauncher::launch)
        }

    private var onRuntimePermissionResult: ((Boolean) -> Unit)? = null
    private val runtimePermissionLauncher = launcherRegistrator
        .registerActivityResultLauncher(ActivityResultContracts.RequestPermission()) {
            onRuntimePermissionResult?.invoke(it)
            onRuntimePermissionResult = null
        }

    private fun askRuntimePermission(permission: String, onResult: (Boolean) -> Unit) =
        if (checkPermission(permission))
            onResult(true)
        else {
            onRuntimePermissionResult = onResult
            runtimePermissionLauncher.launch(permission)
        }

    private fun askMultipleRuntimePermissions(
        permissions: List<String>,
        lifecycleCoroutineScope: LifecycleCoroutineScope,
        onResult: (Boolean) -> Unit
    ) = lifecycleCoroutineScope.launch(Dispatchers.Main) {
        val results = mutableListOf<Boolean>()
        permissions.forEach { permission ->
            val isGranted = checkPermission(permission) || suspendCoroutine {  continuation ->
                askRuntimePermission(permission) { continuation.resumeWith(Result.success(it)) }
            }
            results.add(isGranted)
        }
        onResult(results.all { isGranted -> isGranted })
    }

    private fun checkPermission(permission: String) =
        App.instance.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    fun askContactsPermission(onResult: (Boolean) -> Unit) =
        askRuntimePermission(Manifest.permission.READ_CONTACTS, onResult)

    fun askOutgoingCallPermissions(
        lifecycleCoroutineScope: LifecycleCoroutineScope,
        onResult: (Boolean) -> Unit
    ) = askMultipleRuntimePermissions(
        listOf(Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE),
        lifecycleCoroutineScope,
        onResult
    )

    fun askStoragePermissions(
        lifecycleCoroutineScope: LifecycleCoroutineScope,
        onResult: (Boolean) -> Unit
    ) = askMultipleRuntimePermissions(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        else
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
        lifecycleCoroutineScope,
        onResult
    )

    @SuppressLint("NewApi")
    fun openDefaultPhoneSelection(context: Context) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            context.startActivity(Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS))
        else {
            val sysDialer = context.getSystemService(TelecomManager::class.java).systemDialerPackage
            Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                .putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, sysDialer)
                .let(context::startActivity)
        }

}