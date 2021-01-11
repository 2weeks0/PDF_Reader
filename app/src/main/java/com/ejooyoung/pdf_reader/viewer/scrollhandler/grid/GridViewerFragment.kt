package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.mvvm.BaseFragment
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.databinding.FragmentGridViewerBinding

class GridViewerFragment : BaseFragment<GridViewerViewModel, FragmentGridViewerBinding>() {

    private lateinit var book: Book

    companion object {
        fun newInstance(book: Book): GridViewerFragment {
            return GridViewerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Const.KEY_BUNDLE_BOOK, book)
                }
            }
        }
    }

    override fun setupViewModel() {
        book = requireArguments().getParcelable(Const.KEY_BUNDLE_BOOK)!!
        viewModel = ViewModelFactories.of(
            requireActivity().application,
            this,
            book
        ).create(GridViewerViewModel::class.java)
    }

    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentGridViewerBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
    }

    override fun setupObserver() {
        viewModel.itemList.observe(this, Observer {
            (binding.recyclerView.adapter as GridViewerAdapter).setItem(it)
        })
    }

    override fun onBindingCreated() {
        setupRecyclerView()
        viewModel.loadPdfThumbnailList(book)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = GridViewerAdapter(
            Glide.with(this),
            book.lastPage,
            book.currentPage
        ).apply { setHasStableIds(true) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DevLogger.w()
        (binding.recyclerView.layoutManager as GridLayoutManager)
            .scrollToPositionWithOffset(book.currentPage, 300)
    }
}