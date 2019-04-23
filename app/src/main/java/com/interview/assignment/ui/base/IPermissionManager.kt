package com.interview.assignment.ui.base

import android.Manifest

interface IPermissionManager {

    companion object {
        val CAMERA = arrayOf(Manifest.permission.CAMERA)
        val READWRITE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val ALL = CAMERA + READWRITE
    }

    fun goToSettings()
    fun requestPermission(permissions: Array<String>, listener: CallBackListener)

    interface CallBackListener {
        fun onGiven()
        fun onDenied()
        fun onPermanentDenied()
    }
}