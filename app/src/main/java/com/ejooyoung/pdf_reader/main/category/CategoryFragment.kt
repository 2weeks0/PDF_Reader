package com.ejooyoung.pdf_reader.main.category

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.mvvm.BaseFragment
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.databinding.FragmentCategoryBinding
import com.ejooyoung.pdf_reader.main.MainActivity

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
            onClickCategoryListener = this@CategoryFragment.viewModel
        }
    }

    override fun setupObserver() {
        viewModel.itemList.observe(this, Observer {
            (binding.rvCategory.adapter as CategoryAdapter).setItem(it)
        })
    }

    override fun onBindingCreated() {
        binding.rvCategory.adapter = CategoryAdapter(viewModel).apply {
            setHasStableIds(true)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Const.KEY_REQUEST_ADD_CATEGORY_TO_BOOK && resultCode == Activity.RESULT_OK) {
            DevLogger.i()
            viewModel.onFinishAddCategoryToBook()
        }
    }
}