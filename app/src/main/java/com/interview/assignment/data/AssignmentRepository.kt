package com.interview.assignment.data

import com.interview.assignment.data.files.FilesDataSource
import com.interview.assignment.data.local.LocalDataSource

class AssignmentRepository private constructor(
    private val localDataSource: LocalDataSource,
    private val filesDataSource: FilesDataSource
) {

    fun local(): LocalDataSource {
        return localDataSource
    }

    fun files(): FilesDataSource {
        return filesDataSource
    }

    companion object {

        private var INSTANCE: AssignmentRepository? = null

        fun instance(localDataSource: LocalDataSource, filesDataSource: FilesDataSource): AssignmentRepository {
            if (INSTANCE == null) {
                INSTANCE = AssignmentRepository(localDataSource, filesDataSource)
            }
            return INSTANCE!!
        }
    }
}
