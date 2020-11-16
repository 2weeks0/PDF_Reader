package com.ejooyoung.pdf_reader.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.utils.Logger
import com.ejooyoung.pdf_reader.databinding.LayoutScrollHandlerBinding
import com.ejooyoung.pdf_reader.model.Book
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.ScrollHandle

@SuppressLint("ViewConstructor")
class ScrollHandler  constructor(
    context: Context,
    private val book: Book
) : RelativeLayout(context), ScrollHandle {

    private lateinit var binding: LayoutScrollHandlerBinding

    init {
        initView()
    }

    private fun initView() {
        binding = LayoutScrollHandlerBinding.inflate(LayoutInflater.from(context)).apply {
            book = this@ScrollHandler.book
        }
        addView(binding.root)
    }
    override fun setScroll(position: Float) {

    }

    override fun setupLayout(pdfView: PDFView?) {
        visibility = GONE
        pdfView?.addView(this)
    }

    override fun destroyLayout() {

    }

    override fun setPageNum(pageNum: Int) {

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

}