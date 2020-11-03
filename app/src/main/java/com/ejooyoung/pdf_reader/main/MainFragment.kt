package com.ejooyoung.pdf_reader.main

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.databinding.FragmentMainBinding
import com.ejooyoung.pdf_reader.util.ext.getViewModelFactory
import com.ejooyoung.pdf_reader.viewer.ViewerActivity
import com.shockwave.pdfium.PdfiumCore
import java.io.File
import java.io.FileOutputStream


class MainFragment : Fragment(), OnClickPdfItemListener {

    private val viewModel by viewModels<MainViewModel> { getViewModelFactory() }
    private lateinit var binding: FragmentMainBinding
    private lateinit var mainAdapter: MainAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        setupDataBinding(view)
        setupRecyclerView()
        setupObserver()
        return view
    }

    private fun setupDataBinding(view: View) {
        binding = FragmentMainBinding.bind(view).apply {

        }
    }

    private fun setupRecyclerView() {
        binding.rv.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        mainAdapter = MainAdapter(this).apply {
//            setHasStableIds(true)
        }
        binding.rv.adapter = mainAdapter
    }

    private fun setupObserver() {
        viewModel.pdfList.observe(viewLifecycleOwner, Observer { mainAdapter.setItem(it) })
    }

    override fun onClickItem(uri: Uri) {
        val intent = Intent(requireContext(), ViewerActivity::class.java).apply {
            data = uri
        }
        startActivity(intent)
    }
}