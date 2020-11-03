package com.ejooyoung.pdf_reader.util.ext

import androidx.fragment.app.Fragment
import com.ejooyoung.pdf_reader.MainApplication
import com.ejooyoung.pdf_reader.util.ViewModelFactory

fun Fragment.getViewModelFactory(): ViewModelFactory =
    ViewModelFactory(requireActivity().application as MainApplication, this)