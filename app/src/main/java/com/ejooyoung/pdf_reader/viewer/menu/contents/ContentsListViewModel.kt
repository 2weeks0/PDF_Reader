package com.ejooyoung.pdf_reader.viewer.menu.contents

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.base.repository.PdfDocumentRepository
import com.ejooyoung.pdf_reader.database.model.Contents

class ContentsListViewModel(
        application: Application,
        pdfDocumentRepository: PdfDocumentRepository
): AndroidViewModel(application) {

    companion object {
        fun newInstance(
                application: Application,
                pdfDocumentRepository: PdfDocumentRepository
        ): ContentsListViewModel {
            return ContentsListViewModel(application, pdfDocumentRepository)
        }
    }

    val contentsItemList: MutableLiveData<List<Contents>> = MutableLiveData()
    val visibilityOfEmptyView: ObservableBoolean = ObservableBoolean(false)

    init {
        contentsItemList.value = pdfDocumentRepository.pdfDocumentBookmarkList
    }
}