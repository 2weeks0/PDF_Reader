package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.databinding.FragmentViewerSettingBinding

class ViewerSettingFragment : Fragment() {

    private lateinit var binding: FragmentViewerSettingBinding
    private lateinit var viewModel: ViewerSettingViewModel

    companion object {
        fun newInstance(): ViewerSettingFragment {
            return ViewerSettingFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_viewer_setting, container, false)
        setupViewModel()
        setupDataBinding(view)
        return view
    }

    private fun setupViewModel() {
        viewModel = ViewModelFactories.of(requireActivity().application, this)
            .create(ViewerSettingViewModel::class.java)
    }

    private fun setupDataBinding(view: View) {
        binding = FragmentViewerSettingBinding.bind(view).apply {
            viewModel = this@ViewerSettingFragment.viewModel
        }
    }
}