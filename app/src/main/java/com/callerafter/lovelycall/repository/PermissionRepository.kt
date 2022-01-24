package com.callerafter.lovelycall.repository

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.role.RoleManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.telecom.TelecomManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.callerafter.lovelycall.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class PermissionRepository(private val componentActivity: ComponentActivity) {

    val hasCallerPermission: Boolean get() = with(App.instance) {
        getSystemService(TelecomManager::class.java).defaultDialerPackage == packageName
    }
    val hasOverlayPermission: Boolean get() = Settings.canDrawOverlays(App.instance)
    val hasContactsPermission: Boolean
        get() = checkPermission(Manifest.permission.READ_CONTACTS)

    val hasNecessaryPermissions
        get() = hasCallerPermission && hasOverlayPermission && hasContactsPermission

    private var onCallerPermissionResult: ((Boolean) -> Unit)? = null
    private val callerPermissionLauncher = componentActivity
        .registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
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
    private val overlayPermissionLauncher = componentActivity
        .registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
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
    private val runtimePermissionLauncher = componentActivity
        .registerForActivityResult(ActivityResultContracts.RequestPermission()) {
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
        onResult: (Boolean) -> Unit
    ) = componentActivity.lifecycleScope.launch(Dispatchers.Main) {
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

    fun askOutgoingCallPermissions(onResult: (Boolean) -> Unit) =
        askMultipleRuntimePermissions(
            listOf(Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE),
            onResult
        )

    fun askStoragePermissions(onResult: (Boolean) -> Unit) =
        askMultipleRuntimePermissions(
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            onResult
        )

}