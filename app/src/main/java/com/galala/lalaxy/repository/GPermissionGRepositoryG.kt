package com.galala.lalaxy.repository

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
import com.galala.lalaxy.GAppG
import com.galala.lalaxy.base.GLauncherGRegistratorG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class GPermissionGRepositoryG(launcherRegistrator: GLauncherGRegistratorG) {

    val hasCallerPermission: Boolean get() = with(GAppG.instance) {
        println("")
        getSystemService(TelecomManager::class.java).defaultDialerPackage == packageName
    }
    val hasOverlayPermission: Boolean get() = Settings.canDrawOverlays(GAppG.instance)
    val hasContactsPermission: Boolean
        get() = checkPermission(Manifest.permission.READ_CONTACTS)

    val hasNecessaryPermissions
        get() = hasCallerPermission && hasOverlayPermission && hasContactsPermission

    private var onCallerPermissionResult: ((Boolean) -> Unit)? = null
    private val callerPermissionLauncher = launcherRegistrator
        .registerActivityResultLauncher(ActivityResultContracts.StartActivityForResult()) {
            println("")
            onCallerPermissionResult?.invoke(it.resultCode == RESULT_OK)
            println("")
            onCallerPermissionResult = null
            println("")
        }

    fun askCallerPermission(onResult: (Boolean) -> Unit) =
        if (hasCallerPermission)
            onResult(true)
        else {
            println("")
            onCallerPermissionResult = onResult
            println("")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                GAppG.instance.getSystemService(RoleManager::class.java)
                    .createRequestRoleIntent(RoleManager.ROLE_DIALER)
            else {
                println("")
                Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                    .putExtra(
                        TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME,
                        GAppG.instance.packageName
                    )
            }.let(callerPermissionLauncher::launch)
            println("")
        }

    private var onOverlayPermissionResult: ((Boolean) -> Unit)? = null
    private val overlayPermissionLauncher = launcherRegistrator
        .registerActivityResultLauncher(ActivityResultContracts.StartActivityForResult()) {
            println("")
            onOverlayPermissionResult?.invoke(hasOverlayPermission)
            println("")
            onOverlayPermissionResult = null
            println("")
        }

    fun askOverlayPermission(onResult: (Boolean) -> Unit) =
        if (hasOverlayPermission)
            onResult(true)
        else {
            println("")
            onOverlayPermissionResult = onResult
            println("")
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                .setData(Uri.fromParts("package", GAppG.instance.packageName, null))
                .let(overlayPermissionLauncher::launch)
            println("")
        }

    private var onRuntimePermissionResult: ((Boolean) -> Unit)? = null
    private val runtimePermissionLauncher = launcherRegistrator
        .registerActivityResultLauncher(ActivityResultContracts.RequestPermission()) {
            println("")
            onRuntimePermissionResult?.invoke(it)
            println("")
            onRuntimePermissionResult = null
            println("")
        }

    private fun askRuntimePermission(permission: String, onResult: (Boolean) -> Unit) =
        if (checkPermission(permission))
            onResult(true)
        else {
            println("")
            onRuntimePermissionResult = onResult
            println("")
            runtimePermissionLauncher.launch(permission)
            println("")
        }

    private fun askMultipleRuntimePermissions(
        permissions: List<String>,
        lifecycleCoroutineScope: LifecycleCoroutineScope,
        onResult: (Boolean) -> Unit
    ) = lifecycleCoroutineScope.launch(Dispatchers.Main) {
        println("")
        val results = mutableListOf<Boolean>()
        println("")
        permissions.forEach { permission ->
            println("")
            val isGranted = checkPermission(permission) || suspendCoroutine {  continuation ->
                println("")
                askRuntimePermission(permission) { continuation.resumeWith(Result.success(it)) }
            }
            println("")
            results.add(isGranted)
            println("")
        }
        println("")
        onResult(results.all { isGranted -> isGranted })
        println("")
    }

    private fun checkPermission(permission: String) =
        GAppG.instance.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

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
            println("")
            val sysDialer = context.getSystemService(TelecomManager::class.java).systemDialerPackage
            println("")
            Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                .putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, sysDialer)
                .let(context::startActivity)
            println("")
        }

}