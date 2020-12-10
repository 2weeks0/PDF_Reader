package com.ejooyoung.pdf_reader.viewer

import android.view.View
import androidx.databinding.ViewDataBinding
import com.github.barteksc.pdfviewer.PDFView

interface ViewerMenuClickListener {
    fun previousPage(pdfView: PDFView, binding: ViewDataBinding)
    fun nextPage(pdfView: PDFView, binding: ViewDataBinding)
    fun showInfo(view: View)
    fun performUndo(view: View)
    fun showContents(view: View)
    fun addBookmark(view: View)
    fun deleteBookmark(view: View)
    fun showSetting(view: View)
}