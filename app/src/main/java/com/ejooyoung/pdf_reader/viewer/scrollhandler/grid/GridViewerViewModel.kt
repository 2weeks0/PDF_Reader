package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
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

    fun getBookPageCount(): Int {
        return repository.getBookPageCount()
    }

    fun loadPdfThumbnailList() {
        visibilityOfProgressBar.set(true)
        for (i in 0 until getBookPageCount()) {
            val disposable = repository.getThumbnail(i)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    visibilityOfProgressBar.set(false)
                    itemList.value = Pair(i, it)
                }
            compositeDisposable.add(disposable)
        }
    }
}