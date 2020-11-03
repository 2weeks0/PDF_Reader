package com.ejooyoung.pdf_reader.model

import android.net.Uri
import androidx.core.net.toUri
import java.io.File

class PDF (file: File) {

    val name: String = file.name
    val path: String = file.path
    val uri: Uri = file.toUri()
}