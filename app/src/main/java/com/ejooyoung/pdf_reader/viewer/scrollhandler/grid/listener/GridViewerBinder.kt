package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid.listener

import android.graphics.Bitmap

interface GridViewerBinder {
    fun onBindGridViewer(itemList: MutableList<Bitmap?>, position: Int, notify: () -> Unit)
}