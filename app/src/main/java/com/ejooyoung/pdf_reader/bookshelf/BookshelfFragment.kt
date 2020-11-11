package com.ejooyoung.pdf_reader.bookshelf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.databinding.FragmentMainBinding

class BookshelfFragment : Fragment() {

    private val viewModel by viewModels<BookshelfViewModel> {
        ViewModelFactories.of(requireActivity().application, this)
    }
    private lateinit var binding: FragmentMainBinding
    private lateinit var bookshelfAdapter: BookshelfAdapter

    companion object {
        fun newInstance() = BookshelfFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        setupDataBinding(view)
        setupRecyclerView()
        setupObserver()
        return view
    }

    private fun setupDataBinding(view: View) {
        binding = FragmentMainBinding.bind(view).apply {

        }
    }

    private fun setupRecyclerView() {
        binding.rv.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        bookshelfAdapter = BookshelfAdapter(viewModel).apply {
//            setHasStableIds(true)
        }
        binding.rv.adapter = bookshelfAdapter
    }

    private fun setupObserver() {
        viewModel.bookList.observe(viewLifecycleOwner, Observer { bookshelfAdapter.setItem(it) })
    }
}