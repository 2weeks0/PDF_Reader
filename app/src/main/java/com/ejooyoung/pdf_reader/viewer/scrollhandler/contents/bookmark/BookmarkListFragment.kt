package com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.databinding.FragmentBookmarkListBinding
import com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.ContentsClickListener

class BookmarkListFragment : Fragment() {

    companion object {
        fun newInstance(book: Book, contentsClickListener: ContentsClickListener): BookmarkListFragment {
            return BookmarkListFragment().apply {
                this.book = book
                this.contentsClickListener = contentsClickListener
            }
        }
    }

    private lateinit var book: Book
    private lateinit var binding: FragmentBookmarkListBinding
    private val viewModel by viewModels<BookmarkListViewModel> {
        ViewModelFactories.of(requireActivity().application, this, book)
    }
    private lateinit var contentsClickListener: ContentsClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookmark_list, container, false)
        setupDataBinding(view)
        setupRecyclerView()
        setupObserver()
        return view
    }

    private fun setupDataBinding(view: View) {
        binding = FragmentBookmarkListBinding.bind(view).apply {
            viewModel = this@BookmarkListFragment.viewModel
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = BookmarkListAdapter(contentsClickListener)
    }

    private fun setupObserver() {
        viewModel.contentsItemList.observe(viewLifecycleOwner, Observer {
            (binding.recyclerView.adapter as BookmarkListAdapter).setItem(it)
            viewModel.visibilityOfEmptyView.set(it.isEmpty())
        })
    }
}