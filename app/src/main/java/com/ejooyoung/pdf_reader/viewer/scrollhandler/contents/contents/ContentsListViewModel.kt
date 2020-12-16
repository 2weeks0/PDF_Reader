package com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.contents

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.base.repository.PdfDocumentRepository
import com.ejooyoung.pdf_reader.database.model.Contents

class ContentsListViewModel(
        application: Application,
        private val pdfDocumentRepository: PdfDocumentRepository
): BaseAndroidViewModel(application) {

    val contentsItemList: MutableLiveData<List<Contents>> = MutableLiveData()
    val visibilityOfEmptyView: ObservableBoolean = ObservableBoolean(false)

    companion object {
        fun newInstance(
                application: Application,
                pdfDocumentRepository: PdfDocumentRepository
        ): ContentsListViewModel {
            return ContentsListViewModel(application, pdfDocumentRepository)
        }
    }

    init {
        loadContents()
    }

    private fun loadContents() {
        contentsItemList.value = pdfDocumentRepository.pdfDocumentBookmarkList
    }
}