package com.ejooyoung.pdf_reader.viewer

import android.graphics.pdf.PdfDocument
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ejooyoung.pdf_reader.R
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import kotlinx.android.synthetic.main.activity_viewer.*

class ViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)
        setupPdfView()
    }

    private fun setupFragment() {
        
    }

    private fun setupPdfView() {
        pdfView.fromUri(intent.data)
            .defaultPage(0)
//            .onPageChange(this)
            .enableAnnotationRendering(true)
//            .onLoad(this)
            .scrollHandle(DefaultScrollHandle(this))
            .spacing(10) // in dp
//            .onPageError(this)
            .load()
    }
}