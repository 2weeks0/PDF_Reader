package com.ejooyoung.pdf_reader.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.SeekBar
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.databinding.LayoutScrollHandlerBinding
import com.ejooyoung.pdf_reader.model.Book
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.ScrollHandle

@SuppressLint("ViewConstructor")
class ScrollHandler constructor(
    context: Context,
    private val book: Book
) : RelativeLayout(context), ScrollHandle {

    private lateinit var binding: LayoutScrollHandlerBinding
    private lateinit var pdfView: PDFView
    private val onSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            setCurrentPage(p1, toSeekBarFromPdfView = false)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {

        }
    }

    init {
        setupView()
        addView(binding.root)
    }

    private fun setupView() {
        binding = LayoutScrollHandlerBinding.inflate(LayoutInflater.from(context)).apply {
            book = this@ScrollHandler.book
        }
        binding.seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener)
    }

    override fun setScroll(position: Float) {

    }

    override fun setupLayout(pdfView: PDFView) {
        this.pdfView = pdfView
        visibility = GONE
        pdfView.addView(this)
    }

    override fun destroyLayout() {
        pdfView.removeView(this)
    }

    override fun setPageNum(pageNum: Int) {
        setCurrentPage(pageNum, toSeekBarFromPdfView = true)
    }

    override fun shown(): Boolean {
        return visibility == VISIBLE
    }

    override fun show() {
        visibility = VISIBLE
    }

    override fun hide() {
        visibility = GONE
    }

    override fun hideDelayed() {

    }

    private fun setCurrentPage(currentPage: Int, toSeekBarFromPdfView: Boolean) {
        binding.tvSeekBar.text = context.getString(R.string.seek_bar, currentPage.toString(), book.lastPage.toString())
        if (toSeekBarFromPdfView) {
            binding.seekBar.setOnSeekBarChangeListener(null)
            // pdfView 에서 setPageNum 할 때, +1 해서 보내기 때문에 - 해야 함.
            binding.seekBar.progress = currentPage - 1
            binding.seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener)
        }
        else {
            pdfView.jumpTo(currentPage)
        }
    }
}