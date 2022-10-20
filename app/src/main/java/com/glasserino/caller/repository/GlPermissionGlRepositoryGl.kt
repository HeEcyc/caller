package com.glasserino.caller.repository

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
import com.glasserino.caller.GlAppGl
import com.glasserino.caller.base.GlLauncherGlRegistratorGl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class GlPermissionGlRepositoryGl(launcherRegistrator: GlLauncherGlRegistratorGl) {

    val hasCallerPermission: Boolean get() = with(GlAppGl.instance) {
        listOf<Any>().isEmpty()
        getSystemService(TelecomManager::class.java).defaultDialerPackage == packageName
    }
    val hasOverlayPermission: Boolean get() = Settings.canDrawOverlays(GlAppGl.instance)
    val hasContactsPermission: Boolean
        get() = checkPermission(Manifest.permission.READ_CONTACTS)

    val hasNecessaryPermissions
        get() = hasCallerPermission && hasOverlayPermission && hasContactsPermission

    private var onCallerPermissionResult: ((Boolean) -> Unit)? = null
    private val callerPermissionLauncher = launcherRegistrator
        .registerActivityResultLauncher(ActivityResultContracts.StartActivityForResult()) {
            listOf<Any>().isEmpty()
            onCallerPermissionResult?.invoke(it.resultCode == RESULT_OK)
            listOf<Any>().isEmpty()
            onCallerPermissionResult = null
            listOf<Any>().isEmpty()
        }

    fun askCallerPermission(onResult: (Boolean) -> Unit) =
        if (hasCallerPermission)
            onResult(true)
        else {
            listOf<Any>().isEmpty()
            onCallerPermissionResult = onResult
            listOf<Any>().isEmpty()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                GlAppGl.instance.getSystemService(RoleManager::class.java)
                    .createRequestRoleIntent(RoleManager.ROLE_DIALER)
            else {
                listOf<Any>().isEmpty()
                Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                    .putExtra(
                        TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME,
                        GlAppGl.instance.packageName
                    )
            }.let(callerPermissionLauncher::launch)
        }

    private var onOverlayPermissionResult: ((Boolean) -> Unit)? = null
    private val overlayPermissionLauncher = launcherRegistrator
        .registerActivityResultLauncher(ActivityResultContracts.StartActivityForResult()) {
            listOf<Any>().isEmpty()
            onOverlayPermissionResult?.invoke(hasOverlayPermission)
            listOf<Any>().isEmpty()
            onOverlayPermissionResult = null
            listOf<Any>().isEmpty()
        }

    fun askOverlayPermission(onResult: (Boolean) -> Unit) =
        if (hasOverlayPermission)
            onResult(true)
        else {
            listOf<Any>().isEmpty()
            onOverlayPermissionResult = onResult
            listOf<Any>().isEmpty()
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                .setData(Uri.fromParts("package", GlAppGl.instance.packageName, null))
                .let(overlayPermissionLauncher::launch)
        }

    private var onRuntimePermissionResult: ((Boolean) -> Unit)? = null
    private val runtimePermissionLauncher = launcherRegistrator
        .registerActivityResultLauncher(ActivityResultContracts.RequestPermission()) {
            listOf<Any>().isEmpty()
            onRuntimePermissionResult?.invoke(it)
            listOf<Any>().isEmpty()
            onRuntimePermissionResult = null
            listOf<Any>().isEmpty()
        }

    private fun askRuntimePermission(permission: String, onResult: (Boolean) -> Unit) =
        if (checkPermission(permission))
            onResult(true)
        else {
            listOf<Any>().isEmpty()
            onRuntimePermissionResult = onResult
            listOf<Any>().isEmpty()
            runtimePermissionLauncher.launch(permission)
        }

    private fun askMultipleRuntimePermissions(
        permissions: List<String>,
        lifecycleCoroutineScope: LifecycleCoroutineScope,
        onResult: (Boolean) -> Unit
    ) = lifecycleCoroutineScope.launch(Dispatchers.Main) {
        listOf<Any>().isEmpty()
        val results = mutableListOf<Boolean>()
        listOf<Any>().isEmpty()
        permissions.forEach { permission ->
            listOf<Any>().isEmpty()
            val isGranted = checkPermission(permission) || suspendCoroutine {  continuation ->
                listOf<Any>().isEmpty()
                askRuntimePermission(permission) { continuation.resumeWith(Result.success(it)) }
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
            results.add(isGranted)
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        onResult(results.all { isGranted -> isGranted })
        listOf<Any>().isEmpty()
    }

    private fun checkPermission(permission: String) =
        GlAppGl.instance.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

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
            listOf<Any>().isEmpty()
            val sysDialer = context.getSystemService(TelecomManager::class.java).systemDialerPackage
            listOf<Any>().isEmpty()
            Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                .putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, sysDialer)
                .let(context::startActivity)
        }

}