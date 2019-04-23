package com.interview.assignment.ui.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

open class BaseActivity : AppCompatActivity(), IPermissionManager {

    private lateinit var permissionListener: IPermissionManager.CallBackListener

    // region [PERMISSIONS]
    private fun checkPermissions() = hasCameraAccess() && hasReadWriteAccess()

    private fun hasCameraAccess() =
        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    private fun hasReadWriteAccess(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun goToSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
        startActivity(intent)
    }

    override fun requestPermission(permissions: Array<String>, listener: IPermissionManager.CallBackListener) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            this.permissionListener = listener
            if (checkPermissions()) {
                this.permissionListener.onGiven()
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1500)
            }
        } else {
            this.permissionListener.onGiven()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val grantPermissions = grantResults.filter { it == 0 }
        Log.i("Log", "Granted -> $grantPermissions")
        when {
            grantPermissions.isEmpty() -> permissionListener.onPermanentDenied()
            grantPermissions.size < grantResults.size -> permissionListener.onDenied()
            grantPermissions.size == grantResults.size -> permissionListener.onGiven()
        }
    }
    // endregion

    // region [UTILS]
    fun alert(message: String, okBody: () -> Unit) {
        AlertDialog.Builder(this)
        .setMessage(message)
        .setPositiveButton("Yes") { dialog, _ ->
            okBody()
            dialog.dismiss()
        }
        .setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }.create()
        .show()
    }
    // endregion
}
