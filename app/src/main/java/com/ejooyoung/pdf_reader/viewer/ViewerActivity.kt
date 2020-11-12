package com.ejooyoung.pdf_reader.viewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.Logger
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import kotlinx.android.synthetic.main.activity_viewer.*

class ViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)
        setupPdfView()
    }

    private fun setupPdfView() {
        pdfView.fromUri(intent.data)

            .swipeHorizontal(true)
            .enableSwipe(true)
            .pageFling(true)
            .defaultPage(10)
            .pageFitPolicy(FitPolicy.WIDTH)
            .enableAnnotationRendering(true)
            .scrollHandle(DefaultScrollHandle(this))
            .autoSpacing(true)
            .nightMode(true)
            .load()

        Logger.i("pageCount: ${pdfView.pageCount}")
    }
}