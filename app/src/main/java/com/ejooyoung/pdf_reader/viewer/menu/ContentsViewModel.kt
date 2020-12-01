package com.ejooyoung.pdf_reader.viewer.menu

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.base.repository.PdfDocumentRepositoryImpl
import com.ejooyoung.pdf_reader.model.Book
import com.shockwave.pdfium.PdfDocument

class ContentsViewModel(
    application: Application,
    private val book: Book
) : AndroidViewModel(application) {

    companion object {
        fun newInstance(application: Application, book: Book): ContentsViewModel {
            return ContentsViewModel(application, book)
        }
    }

    val contentsLiveData: MutableLiveData<List<PdfDocument.Bookmark>> = MutableLiveData()
    val visibilityOfEmptyView: ObservableBoolean = ObservableBoolean(false)

    init {
        loadContentsList()
    }

    private fun loadContentsList() {
        val repository = PdfDocumentRepositoryImpl.getInstance()
        contentsLiveData.value = repository.contentsList
        visibilityOfEmptyView.set(contentsLiveData.value!!.isEmpty())
    }
}