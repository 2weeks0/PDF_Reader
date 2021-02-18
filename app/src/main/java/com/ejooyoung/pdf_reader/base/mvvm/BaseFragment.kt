package com.ejooyoung.pdf_reader.base.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<A: BaseAndroidViewModel, B: ViewDataBinding> : Fragment() {

    protected lateinit var binding: B
    lateinit var viewModel: A

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()
        setupDataBinding(inflater, container)
        setupObserver()
        onBindingCreated()
        viewModel.onCreate()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroy()
    }

    protected abstract fun setupViewModel()

    protected abstract fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?)

    protected abstract fun setupObserver()

    protected abstract fun onBindingCreated()
}