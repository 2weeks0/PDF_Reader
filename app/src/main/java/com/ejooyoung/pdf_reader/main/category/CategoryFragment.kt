package com.ejooyoung.pdf_reader.main.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.base.mvvm.BaseFragment
import com.ejooyoung.pdf_reader.databinding.FragmentCategoryBinding

class CategoryFragment : BaseFragment<CategoryViewModel, FragmentCategoryBinding>() {

    companion object {
        fun newInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }

    override fun setupViewModel() {
        viewModel = ViewModelFactories.of(requireActivity().application, this)
            .create(CategoryViewModel::class.java)
    }

    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false).apply {
            viewModel = this@CategoryFragment.viewModel
        }
    }

    override fun setupObserver() {
        viewModel.itemList.observe(this, Observer {
            (binding.rvCategory.adapter as CategoryAdapter).setItem(it)
        })
    }

    override fun onBindingCreated() {
        binding.rvCategory.adapter = CategoryAdapter().apply {
            setHasStableIds(true)
        }
    }
}