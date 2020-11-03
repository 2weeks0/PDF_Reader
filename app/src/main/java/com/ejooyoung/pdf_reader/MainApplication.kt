package com.ejooyoung.pdf_reader

import android.app.Application
import android.util.Log
import java.io.File

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        makeThumbDirIfNotExist()
    }

    private fun makeThumbDirIfNotExist() {
        val folder = File(getThumbDir())

        Log.i("LEEJY", "folder exist: ${folder.exists()}")
        if (!folder.exists()) folder.mkdirs()
    }

    fun getThumbDir() = getExternalFilesDir(null)!!.path + "/thumb"

    fun getThumbPath(fileName: String) = getThumbDir() + File.separator + fileName.replace("pdf", "png")
}