package com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.contents

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
import com.ejooyoung.pdf_reader.base.repository.PdfDocumentRepositoryImpl
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.databinding.FragmentContentsListBinding
import com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.ContentsClickListener

class ContentsListFragment : Fragment() {

    companion object {
        fun newInstance(contentsClickListener: ContentsClickListener): ContentsListFragment {
            return ContentsListFragment().apply {
                this.contentsClickListener = contentsClickListener
            }
        }
    }

    private lateinit var binding: FragmentContentsListBinding
    private val viewModel by viewModels<ContentsListViewModel> {
        ViewModelFactories.of(requireActivity().application, this, PdfDocumentRepositoryImpl.getInstance())
    }
    private lateinit var contentsClickListener: ContentsClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contents_list, container, false)
        setupDataBinding(view)
        setupRecyclerView()
        setupObserver()
        return view
    }

    private fun setupDataBinding(view: View) {
        binding = FragmentContentsListBinding.bind(view).apply {
            viewModel = this@ContentsListFragment.viewModel
        }
    }

    private fun setupRecyclerView() {
        DevLogger.i()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = ContentsListAdapter(contentsClickListener)
    }

    private fun setupObserver() {
        viewModel.contentsItemList.observe(viewLifecycleOwner, Observer {
            (binding.recyclerView.adapter as ContentsListAdapter).setItem(it)
            viewModel.visibilityOfEmptyView.set(it.isEmpty())
        })
    }
}