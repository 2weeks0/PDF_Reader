package com.ejooyoung.pdf_reader.viewer

import androidx.databinding.ViewDataBinding
import com.github.barteksc.pdfviewer.PDFView

interface ViewerMenuClickListener {
    fun previousPage(pdfView: PDFView, binding: ViewDataBinding)
    fun nextPage(pdfView: PDFView, binding: ViewDataBinding)
    fun showInfo()
    fun performUndo()
    fun showContents(pdfView: PDFView)
    fun showBookmark()
    fun showSetting()
}