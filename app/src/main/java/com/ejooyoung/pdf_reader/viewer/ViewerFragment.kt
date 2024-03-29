package com.ejooyoung.pdf_reader.viewer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.ejooyoung.pdf_reader.BindingAdapter
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference.*
import com.ejooyoung.pdf_reader.application.preference.ViewerPreferenceMap
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.mvvm.BaseFragment
import com.ejooyoung.pdf_reader.base.repository.PdfDocumentRepositoryImpl
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.base.utils.UnitUtils
import com.ejooyoung.pdf_reader.databinding.FragmentViewerBinding
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.database.model.Bookmark
import com.github.barteksc.pdfviewer.util.FitPolicy

class ViewerFragment : BaseFragment<ViewerViewModel, FragmentViewerBinding>() {

    private lateinit var book: Book
    private lateinit var glideRequest: RequestManager

    companion object {
        fun newInstance(book: Book) = ViewerFragment().apply {
            arguments = Bundle().apply { putParcelable(Const.KEY_BUNDLE_BOOK, book) }
        }
    }

    override fun setupViewModel() {
        book = requireArguments().getParcelable(Const.KEY_BUNDLE_BOOK)!!
        viewModel = ViewModelFactories.of(
            requireActivity().application,
    this,
            book
        ).create(ViewerViewModel::class.java)
    }

    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentViewerBinding.inflate(inflater, container, false).apply {
            viewModel = this@ViewerFragment.viewModel
            clickListener = this@ViewerFragment.viewModel
        }
    }

    override fun setupObserver() {
        viewModel.currentPage.observe(viewLifecycleOwner, Observer {
            DevLogger.i()
            viewModel.updateIsBookmarkedPage()
            viewModel.book.currentPage = it
            binding.scrollHandler.seekBar.progress = it
            binding.scrollHandler.tvSeekBar.text =
                resources.getString(
                    R.string.seek_bar,
                    (it + 1).toString(),
                    viewModel.book.lastPage.toString()
                )
        })
        viewModel.preferenceMap.observe(viewLifecycleOwner, Observer {
            setupPdfView(it)
        })
        viewModel.previewThumbnail.observe(viewLifecycleOwner, Observer {
           setupPreview(it)
        })
    }

    override fun onBindingCreated() {
        glideRequest = Glide.with(this)
        setupSeekBar()
    }

    private fun setupPdfView(viewerPreferenceMap: ViewerPreferenceMap) {
        DevLogger.i()
        val uri = Uri.parse(viewModel.book.uriString)

        binding.viewPdf.fromUri(uri)
            .nightMode(viewerPreferenceMap[DARK_THEME])
            .swipeHorizontal(viewerPreferenceMap[SWIPE_HORIZONTAL])
            .pageFling(viewerPreferenceMap[FLING])
            .pageFitPolicy(if (viewerPreferenceMap[FIT_WIDTH]) FitPolicy.WIDTH else FitPolicy.HEIGHT)
            .enableDoubletap(viewerPreferenceMap[ZOOM_BY_DOUBLE_TAP])
            .enableSwipe(true)
            .defaultPage(viewModel.currentPage.value!!)
            .enableAnnotationRendering(true)
            .autoSpacing(true)
            .onTap {
                viewModel.visibilityScrollHandler.set(!viewModel.visibilityScrollHandler.get())
                return@onTap true
            }
            .onPageChange { page, _ ->
                viewModel.currentPage.value = page
            }
            .onLoad {
                PdfDocumentRepositoryImpl.getInstance().savePdfDocumentBookmarkList(binding.viewPdf.tableOfContents)
                viewModel.book.lastPage = binding.viewPdf.pageCount
                binding.scrollHandler.seekBar.max = binding.viewPdf.pageCount - 1
            }
            .load()
    }

    private fun setupPreview(bitmap: Bitmap) {
        binding.scrollHandler.ivPreview.setImageBitmap(bitmap)
//        binding.scrollHandler.ivPreview.layoutParams =
//            binding.scrollHandler.ivPreview.layoutParams.apply {
//                if (bitmap.width > bitmap.height) {
//                    width = UnitUtils.dpToPx(requireContext(), 200f)
//                    height = ViewGroup.LayoutParams.WRAP_CONTENT
//                }
//                else {
//                    width = ViewGroup.LayoutParams.WRAP_CONTENT
//                    height = UnitUtils.dpToPx(requireContext(), 200f)
//                }
//            }
    }

    private fun setupSeekBar() {
        binding.scrollHandler.seekBar.max = viewModel.book.lastPage - 1
        binding.scrollHandler.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar!!.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                viewModel.onProgressChanged(seekBar, progress, fromUser)
                binding.scrollHandler.tvSeekBar.text =
                    resources.getString(
                        R.string.seek_bar,
                        (progress + 1).toString(),
                        viewModel.book.lastPage.toString()
                    )
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                viewModel.onStartTracking(seekBar!!)
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.onStopTracking(seekBar!!, binding.viewPdf)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Const.KEY_REQUEST_RENAME ->
                    data?.getParcelableExtra<Bookmark>(Const.KEY_BUNDLE_RENAMABLE)?.let {
                        viewModel.updateRenamedBookmark(it)
                    }
                Const.KEY_REQUEST_OPEN_CONTENTS, Const.KEY_REQUEST_OPEN_GRID_VIEWER ->
                    data?.getIntExtra(Const.KEY_BUNDLE_PAGE_INDEX, -1)?.let {
                        if (0 <= it) {
                            DevLogger.i("pageIdx: $it")
                            viewModel.currentPage.value = it
                        }
                    }
            }
        }
    }

    fun onVolumeKeyDown(keyCode: Int): Boolean {
        return viewModel.onVolumeKeyDown(binding.viewPdf, keyCode)
    }
}