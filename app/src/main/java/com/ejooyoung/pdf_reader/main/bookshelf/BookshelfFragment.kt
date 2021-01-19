package com.ejooyoung.pdf_reader.main.bookshelf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.base.mvvm.BaseFragment
import com.ejooyoung.pdf_reader.databinding.FragmentBookshelfBinding

class BookshelfFragment : BaseFragment<BookshelfViewModel, FragmentBookshelfBinding>() {

    private lateinit var bookshelfAdapter: BookshelfAdapter

    companion object {
        fun newInstance() = BookshelfFragment()
    }

    override fun setupViewModel() {
        viewModel = ViewModelFactories.of(requireActivity().application, this)
            .create(BookshelfViewModel::class.java)
    }

    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentBookshelfBinding.inflate(inflater, container, false).apply {
            viewModel = this@BookshelfFragment.viewModel
        }
    }

    override fun setupObserver() {
        viewModel.bookList.observe(viewLifecycleOwner, Observer {
            bookshelfAdapter.setItem(it)
        })
    }

    override fun onBindingCreated() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rv.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        bookshelfAdapter = BookshelfAdapter(viewModel).apply {
            setHasStableIds(true)
        }
        binding.rv.adapter = bookshelfAdapter
    }
}