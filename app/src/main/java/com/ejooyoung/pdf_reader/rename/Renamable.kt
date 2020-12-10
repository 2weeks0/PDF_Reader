package com.ejooyoung.pdf_reader.rename

import android.os.Parcelable

interface Renamable: Parcelable {
    fun getInitialName(): String
    fun rename(newName: String)
}