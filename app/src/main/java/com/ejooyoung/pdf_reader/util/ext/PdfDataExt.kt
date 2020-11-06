package com.ejooyoung.pdf_reader.util.ext

import androidx.core.net.toUri
import com.ejooyoung.pdf_reader.model.MyPDF
import java.io.File

fun MyPDF.getUri() = File(path).toUri()

