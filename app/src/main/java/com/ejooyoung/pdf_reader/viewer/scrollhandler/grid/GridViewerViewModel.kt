package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.database.model.Book
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class GridViewerViewModel private constructor(
    application: Application,
    private val repository: GridViewerRepository
) : BaseAndroidViewModel(application) {

    val itemList: MutableLiveData<Pair<Int, Bitmap>> = MutableLiveData()

    companion object {
        fun newInstance(
            application: Application,
            repository: GridViewerRepository
        ): GridViewerViewModel {
            return GridViewerViewModel(application, repository)
        }
    }

    fun loadPdfThumbnailList(book: Book) {
        DevLogger.i()
        val disposable = Completable.fromAction {
            var left = book.currentPage
            var right = book.currentPage
            loadPdfThumbnail(book, book.currentPage)
            do {
                if (0 <= --left) loadPdfThumbnail(book, left)
                if (++right < book.lastPage) loadPdfThumbnail(book, right)
            } while (0 <= left || right < book.lastPage)
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                DevLogger.e("complete")
            }
        compositeDisposable.add(disposable)
    }

    private fun loadPdfThumbnail(book: Book, index: Int) {
        DevLogger.w("index: $index")
        val disposable = repository.getThumbnail(getApplication(), book, index)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                itemList.value = Pair(index, it)
            }
        compositeDisposable.add(disposable)
    }
}