package com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.base.mvvm.BaseFragment
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.databinding.FragmentBookmarkListBinding
import com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.ContentsClickListener

class BookmarkListFragment : BaseFragment<BookmarkListViewModel, FragmentBookmarkListBinding>() {

    private lateinit var book: Book
    private lateinit var contentsClickListener: ContentsClickListener

    companion object {
        fun newInstance(book: Book, contentsClickListener: ContentsClickListener): BookmarkListFragment {
            return BookmarkListFragment().apply {
                this.book = book
                this.contentsClickListener = contentsClickListener
            }
        }
    }

    override fun setupViewModel() {
        viewModel = ViewModelFactories.of(
            requireActivity().application,
            this,
            book
        ).create(BookmarkListViewModel::class.java)
    }

    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentBookmarkListBinding.inflate(inflater, container, false).apply {
            viewModel = this@BookmarkListFragment.viewModel
        }
    }

    override fun setupObserver() {
        viewModel.contentsItemList.observe(viewLifecycleOwner, Observer {
            (binding.recyclerView.adapter as BookmarkListAdapter).setItem(it)
            viewModel.visibilityOfEmptyView.set(it.isEmpty())
        })
    }

    override fun onBindingCreated() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = BookmarkListAdapter(contentsClickListener)
    }
}