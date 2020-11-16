package com.ejooyoung.pdf_reader.viewer

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.base.utils.Logger
import com.ejooyoung.pdf_reader.base.widget.ScrollHandler
import com.ejooyoung.pdf_reader.databinding.FragmentViewerBinding
import com.ejooyoung.pdf_reader.model.Book
import com.github.barteksc.pdfviewer.util.FitPolicy

class ViewerFragment : Fragment() {

    private lateinit var book: Book
    private lateinit var binding: FragmentViewerBinding
    private val viewModel by viewModels<ViewerViewModel> {
        ViewModelFactories.of(requireActivity().application, this, book)
    }

    companion object {
        fun newInstance(book: Book) = ViewerFragment().apply { this.book = book }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_viewer, container, false)
        binding = FragmentViewerBinding.bind(view).apply {
            viewModel = this@ViewerFragment.viewModel
        }
        setupPdfView(book)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.updateBook()
    }

    private fun setupPdfView(book: Book) {
        Uri.parse(book.uriString)?.let {
            binding.viewPdf.fromUri(it)
                .swipeHorizontal(true)
                .enableSwipe(true)
                .pageFling(true)
                .defaultPage(book.currentPage)
                .pageFitPolicy(FitPolicy.WIDTH)
                .enableAnnotationRendering(true)
                .scrollHandle(ScrollHandler(requireContext(), book))
                .autoSpacing(true)
                .nightMode(false)
                .onPageChange { page, pageCount ->
                    Logger.d("$page / $pageCount")
                    if (book.lastPage == 0) book.lastPage = pageCount
                    book.currentPage = page
                }
                .load()
        }
    }
}