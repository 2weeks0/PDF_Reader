package com.ejooyoung.pdf_reader.main.category.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.base.mvvm.BaseFragment
import com.ejooyoung.pdf_reader.databinding.FragmentSettingCategoryBinding

class SettingCategoryFragment :
    BaseFragment<SettingCategoryViewModel, FragmentSettingCategoryBinding>() {

    companion object {
        fun newInstance(): SettingCategoryFragment {
            return SettingCategoryFragment()
        }
    }

    override fun setupViewModel() {
        viewModel = ViewModelFactories.of(requireActivity().application, this)
            .create(SettingCategoryViewModel::class.java)
    }

    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentSettingCategoryBinding.inflate(inflater, container, false).apply {
            viewModel = this@SettingCategoryFragment.viewModel
        }
    }

    override fun setupObserver() {
        viewModel.itemList.observe(this, Observer {
            (binding.rvCategory.adapter as SettingCategoryAdapter).setItem(it)
        })
    }

    override fun onBindingCreated() {
        binding.rvCategory.adapter = SettingCategoryAdapter().apply {
            setHasStableIds(true)
        }
    }
}