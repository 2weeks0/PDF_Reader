package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.base.mvvm.BaseFragment
import com.ejooyoung.pdf_reader.databinding.FragmentSettingTouchZoneBinding

class SettingTouchZoneFragment
    : BaseFragment<SettingTouchZoneViewModel, FragmentSettingTouchZoneBinding>() {

    companion object {
        fun newInstance(): SettingTouchZoneFragment {
            return SettingTouchZoneFragment()
        }
    }

    override fun setupViewModel() {
        viewModel = ViewModelFactories.of(requireActivity().application, this)
            .create(SettingTouchZoneViewModel::class.java)
    }

    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentSettingTouchZoneBinding.inflate(inflater, container, false)
            .apply {

            }
    }

    override fun setupObserver() {

    }

    override fun onBindingCreated() {

    }
}