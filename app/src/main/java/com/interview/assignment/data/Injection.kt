package com.interview.assignment.data

import android.content.Context
import com.interview.assignment.data.files.FilesDataSource
import com.interview.assignment.data.local.AssignmentDatabase
import com.interview.assignment.data.local.LocalDataSource

object Injection {

    private var INSTANCE: AssignmentRepository? = null

    fun createRepo(context: Context): AssignmentRepository? {
        if (INSTANCE == null) {

            val file = context.getExternalFilesDir(null)
            val database = AssignmentDatabase.instance(context)
            INSTANCE = AssignmentRepository.instance(LocalDataSource.instance(database), FilesDataSource.instance(file!!))
        }
        return INSTANCE
    }

    fun repo(): AssignmentRepository? {
        return INSTANCE
    }
}
