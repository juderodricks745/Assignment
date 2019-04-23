package com.interview.assignment.data.files

import java.io.File

interface DataSource {

    fun initAssignmentSpace()
    fun createImageCaptureFile(): File
}
