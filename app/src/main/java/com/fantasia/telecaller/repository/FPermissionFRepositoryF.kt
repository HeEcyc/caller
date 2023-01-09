package com.fantasia.telecaller.repository

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
import com.fantasia.telecaller.FAppF
import com.fantasia.telecaller.FCMService
import com.fantasia.telecaller.base.FLauncherFRegistratorF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class FPermissionFRepositoryF(launcherRegistrator: FLauncherFRegistratorF) {

    val hasCallerPermission: Boolean get() = with(FAppF.instance) {
        " "[0]
        getSystemService(TelecomManager::class.java).defaultDialerPackage == packageName
    }
    val hasOverlayPermission: Boolean get() = Settings.canDrawOverlays(FAppF.instance)
    val hasContactsPermission: Boolean
        get() = checkPermission(Manifest.permission.READ_CONTACTS)

    val hasNecessaryPermissions
        get() = hasCallerPermission && hasOverlayPermission && hasContactsPermission

    private var onCallerPermissionResult: ((Boolean) -> Unit)? = null
    private val callerPermissionLauncher = launcherRegistrator
        .registerActivityResultLauncher(ActivityResultContracts.StartActivityForResult()) {
            " "[0]
            onCallerPermissionResult?.invoke(it.resultCode == RESULT_OK)
            " "[0]
            onCallerPermissionResult = null
            " "[0]
        }

    fun askCallerPermission(onResult: (Boolean) -> Unit) =
        if (hasCallerPermission)
            onResult(true)
        else {
            " "[0]
            onCallerPermissionResult = onResult
            " "[0]
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                FAppF.instance.getSystemService(RoleManager::class.java)
                    .createRequestRoleIntent(RoleManager.ROLE_DIALER)
            else {
                " "[0]
                Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                    .putExtra(
                        TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME,
                        FAppF.instance.packageName
                    )
            }.let(callerPermissionLauncher::launch)
        }

    private var onOverlayPermissionResult: ((Boolean) -> Unit)? = null
    private val overlayPermissionLauncher = launcherRegistrator
        .registerActivityResultLauncher(ActivityResultContracts.StartActivityForResult()) {
            " "[0]
            onOverlayPermissionResult?.invoke(hasOverlayPermission)
            " "[0]
            onOverlayPermissionResult = null
            " "[0]
        }

    fun askOverlayPermission(onResult: (Boolean) -> Unit) =
        if (hasOverlayPermission){
            onResult(true)
        }

        else {
            " "[0]
            onOverlayPermissionResult = onResult
            " "[0]
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                .setData(Uri.fromParts("package", FAppF.instance.packageName, null))
                .let(overlayPermissionLauncher::launch)
        }

    private var onRuntimePermissionResult: ((Boolean) -> Unit)? = null
    private val runtimePermissionLauncher = launcherRegistrator
        .registerActivityResultLauncher(ActivityResultContracts.RequestPermission()) {
            " "[0]
            onRuntimePermissionResult?.invoke(it)
            " "[0]
            onRuntimePermissionResult = null
            " "[0]
        }

    private fun askRuntimePermission(permission: String, onResult: (Boolean) -> Unit) =
        if (checkPermission(permission))
            onResult(true)
        else {
            " "[0]
            onRuntimePermissionResult = onResult
            " "[0]
            runtimePermissionLauncher.launch(permission)
        }

    private fun askMultipleRuntimePermissions(
        permissions: List<String>,
        lifecycleCoroutineScope: LifecycleCoroutineScope,
        onResult: (Boolean) -> Unit
    ) = lifecycleCoroutineScope.launch(Dispatchers.Main) {
        " "[0]
        val results = mutableListOf<Boolean>()
        " "[0]
        permissions.forEach { permission ->
            " "[0]
            val isGranted = checkPermission(permission) || suspendCoroutine {  continuation ->
                " "[0]
                askRuntimePermission(permission) { continuation.resumeWith(Result.success(it)) }
                " "[0]
            }
            " "[0]
            results.add(isGranted)
            " "[0]
        }
        " "[0]
        onResult(results.all { isGranted -> isGranted })
        " "[0]
    }

    private fun checkPermission(permission: String) =
        FAppF.instance.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

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
            " "[0]
            val sysDialer = context.getSystemService(TelecomManager::class.java).systemDialerPackage
            " "[0]
            Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                .putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, sysDialer)
                .let(context::startActivity)
        }

}