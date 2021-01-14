package com.ejooyoung.pdf_reader.viewer.listener

import android.widget.SeekBar
import com.github.barteksc.pdfviewer.PDFView

interface PreviewListener {
    fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean)
    fun onStartTracking(seekBar: SeekBar)
    fun onStopTracking(seekBar: SeekBar, pdfView: PDFView)
}