package com.ejooyoung.pdf_reader.viewer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.repository.PdfDocumentRepositoryImpl
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.databinding.FragmentViewerBinding
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.database.model.Bookmark
import com.github.barteksc.pdfviewer.util.FitPolicy

class ViewerFragment : Fragment() {

    private lateinit var book: Book
    private lateinit var binding: FragmentViewerBinding
    private val viewModel by viewModels<ViewerViewModel> {
        ViewModelFactories.of(requireActivity().application, this, book)
    }

    companion object {
        fun newInstance(book: Book) = ViewerFragment().apply {
            this.book = book
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_viewer, container, false)
        binding = FragmentViewerBinding.bind(view).apply {
            viewModel = this@ViewerFragment.viewModel
            clickListener = this@ViewerFragment.viewModel
        }
        setupPdfView()
        setupSeekBar()
        setupObserver()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.updateBook()
    }

    private fun setupObserver() {
        viewModel.currentPage.observe(viewLifecycleOwner, Observer {
            DevLogger.i()
            viewModel.updateIsBookmarkedPage()
            binding.scrollHandler.seekBar.progress = it
            binding.scrollHandler.tvSeekBar.text =
                    resources.getString(R.string.seek_bar, (it + 1).toString(), viewModel.book.lastPage.toString())
        })
    }

    private fun setupPdfView() {
        val uri = Uri.parse(viewModel.book.uriString)
        binding.viewPdf.fromUri(uri)
            .swipeHorizontal(true)
            .enableSwipe(true)
            .pageFling(true)
            .defaultPage(viewModel.book.currentPage)
            .pageFitPolicy(FitPolicy.WIDTH)
            .enableAnnotationRendering(true)
            .autoSpacing(true)
            .nightMode(false)
            .onTap {
                viewModel.visibilityScrollHandler.set(!viewModel.visibilityScrollHandler.get())
                return@onTap true
            }
            .onPageChange { page, pageCount ->
                DevLogger.d("${(page + 1)} / $pageCount")
                    if (viewModel.book.lastPage == 0) {
                        viewModel.book.lastPage = pageCount
                        binding.scrollHandler.seekBar.max = pageCount - 1
                    }
                viewModel.currentPage.value = page
            }
            .onLoad {
                PdfDocumentRepositoryImpl.getInstance().savePdfDocumentBookmarkList(binding.viewPdf.tableOfContents)
            }
            .load()
    }

    private fun setupSeekBar() {
        binding.scrollHandler.seekBar.max = viewModel.book.lastPage - 1
        binding.scrollHandler.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                DevLogger.d("fromUser: $fromUser")
                if (fromUser) {
                    binding.viewPdf.jumpTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Const.KEY_REQUEST_RENAME -> {
                    data?.getParcelableExtra<Bookmark>(Const.KEY_BUNDLE_RENAMABLE)?.let {
                        viewModel.updateRenamedBookmark(it)
                    }
                }
            }
        }
    }
}