package com.ejooyoung.pdf_reader.viewer.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.databinding.DialogContentsBinding
import com.ejooyoung.pdf_reader.model.Book
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shockwave.pdfium.PdfDocument

class ContentsDialog private constructor(
    private val contentsList: MutableList<PdfDocument.Bookmark>,
    private val book: Book
) : BottomSheetDialogFragment() {

    private lateinit var binding: DialogContentsBinding

    companion object {
        fun newInstance(
            contentsList: MutableList<PdfDocument.Bookmark>,
            book: Book
        ): ContentsDialog {
            return ContentsDialog(contentsList, book)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DevLogger.i()
        val view = inflater.inflate(R.layout.dialog_contents, container, false)
        setupDataBinding(view)
        setupRecyclerView()
        return view
    }

    private fun setupDataBinding(view: View) {
        binding = DialogContentsBinding.bind(view).apply {
            book = this@ContentsDialog.book
            size = contentsList.size
        }
    }

    private fun setupRecyclerView() {
        binding.recycler.let {
            it.adapter = ContentsAdapter(contentsList)
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}