package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.application.PreferenceType.*
import com.ejooyoung.pdf_reader.base.mvvm.BaseFragment
import com.ejooyoung.pdf_reader.databinding.FragmentViewerSettingBinding

class ViewerSettingFragment : BaseFragment<ViewerSettingViewModel, FragmentViewerSettingBinding>() {

    companion object {
        fun newInstance(): ViewerSettingFragment {
            return ViewerSettingFragment()
        }
    }

    override fun setupViewModel() {
        viewModel = ViewModelFactories.of(requireActivity().application, this)
            .create(ViewerSettingViewModel::class.java)
    }

    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentViewerSettingBinding.inflate(inflater, container, false).apply {
            viewModel = this@ViewerSettingFragment.viewModel
        }
    }

    override fun setupObserver() {
        viewModel.preferenceMap.observe(viewLifecycleOwner, Observer {
            binding.layDarkTheme.btnSwitch.isChecked = it.getOrDefault(VIEWER_DARK_THEME, VIEWER_DARK_THEME.defValue)
        })
    }

    override fun onBindingCreated() {

    }
}