package com.interview.assignment.data.files

import android.util.Log

import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class FilesDataSource private constructor(private val files: File) : DataSource {

    companion object {
        private var source: FilesDataSource? = null

        fun instance(files: File): FilesDataSource {
            if (source == null) {
                source = FilesDataSource(files)
            }
            return source!!
        }
    }

    override fun initAssignmentSpace() {
        Log.i("Log", "File Path -> " + files.absolutePath)
    }

    override fun createImageCaptureFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "JPEG_$timeStamp"
        return File.createTempFile(imageFileName, ".jpg", files)
    }
}
