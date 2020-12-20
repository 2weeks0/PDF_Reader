package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference.*
import com.ejooyoung.pdf_reader.application.preference.ViewerPreferenceMap
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
            initSwitch(it)
        })
    }

    override fun onBindingCreated() {

    }

    private fun initSwitch(viewerPreferenceMap: ViewerPreferenceMap) {
        binding.layTouchZone.btnSwitch.isChecked = viewerPreferenceMap[TOUCH_ZONE]
        binding.layDarkTheme.btnSwitch.isChecked = viewerPreferenceMap[DARK_THEME]
        binding.laySwipeHorizontal.btnSwitch.isChecked = viewerPreferenceMap[SWIPE_HORIZONTAL]
        binding.layFling.btnSwitch.isChecked = viewerPreferenceMap[FLING]
        binding.layFitWidth.btnSwitch.isChecked = viewerPreferenceMap[FIT_WIDTH]
        binding.layZoomByDoubleTap.btnSwitch.isChecked = viewerPreferenceMap[ZOOM_BY_DOUBLE_TAP]
        binding.layJumpByVolumeKey.btnSwitch.isChecked = viewerPreferenceMap[JUMP_BY_VOLUME_KEY]
    }
}