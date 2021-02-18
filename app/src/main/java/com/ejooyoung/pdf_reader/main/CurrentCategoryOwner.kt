package com.ejooyoung.pdf_reader.main

import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.main.model.CurrentCategory

interface CurrentCategoryOwner {
    fun getCurrentCategory(): MutableLiveData<CurrentCategory>
}