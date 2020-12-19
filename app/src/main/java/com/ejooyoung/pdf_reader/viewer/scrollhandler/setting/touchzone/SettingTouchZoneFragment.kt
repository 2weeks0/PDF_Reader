package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
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
                viewModel = this@SettingTouchZoneFragment.viewModel
            }
    }

    override fun setupObserver() {

    }

    override fun onBindingCreated() {
        setupSeekBar()
    }

    private fun setupSeekBar() {
        binding.settingWidth.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.onTouchZoneWidthChanged(progress)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        binding.settingHeight.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.onTouchZoneHeightChanged(progress)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        binding.settingMargin.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.onTouchZoneMarginChanged(progress)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }
}