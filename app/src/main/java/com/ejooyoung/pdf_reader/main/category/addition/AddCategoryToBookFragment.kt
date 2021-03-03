package com.ejooyoung.pdf_reader.main.category.addition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.mvvm.BaseFragment
import com.ejooyoung.pdf_reader.databinding.FragmentAddCategoryToBookBinding

class AddCategoryToBookFragment :
    BaseFragment<AddCategoryToBookViewModel, FragmentAddCategoryToBookBinding>() {

    companion object {
        fun newInstance(categoryGuid: String): AddCategoryToBookFragment {
            return AddCategoryToBookFragment().apply {
                arguments = Bundle().apply {
                    putString(Const.KEY_BUNDLE_CATEGORY, categoryGuid)
                }
            }
        }
    }
    override fun setupViewModel() {
        val categoryGuid = requireArguments().getString(Const.KEY_BUNDLE_CATEGORY)
        viewModel = ViewModelFactories.of(requireActivity().application, this, categoryGuid)
            .create(AddCategoryToBookViewModel::class.java)
    }

    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentAddCategoryToBookBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@AddCategoryToBookFragment.viewModel
            }
    }

    override fun setupObserver() {
        viewModel.itemList.observe(this, Observer {
            (binding.rvAddibleBook.adapter as AddCategoryToBookAdapter).setItem(it)
        })
    }

    override fun onBindingCreated() {
        binding.rvAddibleBook.adapter = AddCategoryToBookAdapter(viewModel).apply {
            setHasStableIds(true)
        }
    }
}