package com.ejooyoung.pdf_reader.base.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<A: BaseAndroidViewModel, B: ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: B
    lateinit var viewModel: A

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupDataBinding()
        setupObserver()
        onBindingCreated()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    protected abstract fun setupViewModel()

    protected abstract fun setupDataBinding()

    protected abstract fun setupObserver()

    protected abstract fun onBindingCreated()
}