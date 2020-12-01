package com.ejooyoung.pdf_reader.viewer.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.databinding.FragmentContentsListBinding

class ContentsListFragment : Fragment() {

    companion object {
        fun newInstance(contentsType: ContentsType): ContentsListFragment {
            return ContentsListFragment().apply {
                this.contentsType = contentsType
            }
        }
    }

    private lateinit var contentsType: ContentsType
    private lateinit var binding: FragmentContentsListBinding

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

        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = ContentsListAdapter { DevLogger.i() }
    }

    private fun setupObserver() {
        (parentFragment as ContentsFragment).viewModel.contentsLiveData.observe(
            viewLifecycleOwner,
            Observer {
                (binding.recyclerView.adapter as ContentsListAdapter).setItem(it)
                binding.emptyView.visibility = it.isEmpty()
            }
        )
    }
}