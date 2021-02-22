package com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.contents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.base.mvvm.BaseFragment
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.databinding.FragmentContentsListBinding
import com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.ContentsClickListener

class ContentsListFragment : BaseFragment<ContentsListViewModel, FragmentContentsListBinding>() {

    private lateinit var contentsClickListener: ContentsClickListener

    companion object {
        fun newInstance(contentsClickListener: ContentsClickListener): ContentsListFragment {
            return ContentsListFragment().apply {
                this.contentsClickListener = contentsClickListener
            }
        }
    }

    override fun setupViewModel() {
        viewModel = ViewModelFactories.of(requireActivity().application, this)
            .create(ContentsListViewModel::class.java)
    }

    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentContentsListBinding.inflate(inflater, container, false).apply {
            viewModel = this@ContentsListFragment.viewModel
        }
    }

    override fun setupObserver() {
        viewModel.contentsItemList.observe(viewLifecycleOwner, Observer {
            (binding.recyclerView.adapter as ContentsListAdapter).setItem(it)
            viewModel.visibilityOfEmptyView.set(it.isEmpty())
        })
    }

    override fun onBindingCreated() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        DevLogger.i()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = ContentsListAdapter(contentsClickListener)
    }
}