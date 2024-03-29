package com.ejooyoung.pdf_reader.viewer.listener

import android.view.View
import com.github.barteksc.pdfviewer.PDFView

interface ViewerMenuClickListener {
    fun previousPage(pdfView: PDFView)
    fun nextPage(pdfView: PDFView)
    fun showInfo(view: View)
    fun showGrid(view: View)
    fun showContents(view: View)
    fun addBookmark(view: View)
    fun deleteBookmark(view: View)
    fun showSetting(view: View)
}