package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.ext.withBorder
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.viewer.scrollhandler.grid.listener.GridViewerBinder
import com.ejooyoung.pdf_reader.viewer.scrollhandler.grid.listener.GridViewerClickListener
import com.shockwave.pdfium.PdfiumCore
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class GridViewerViewModel private constructor(
    application: Application,
    private val repository: GridViewerRepository,
    val book: Book
) : BaseAndroidViewModel(application),
    GridViewerClickListener, GridViewerBinder {

    private val core = PdfiumCore(application)
    private val pdfDocument = core.newDocument(
        getApplication<Application>()
            .contentResolver.openFileDescriptor(Uri.parse(book.uriString), "r")
    )

    companion object {
        fun newInstance(
            application: Application,
            repository: GridViewerRepository,
            book: Book
        ): GridViewerViewModel {
            return GridViewerViewModel(application, repository, book)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        core.closeDocument(pdfDocument)
    }

    override fun onClickGridViewer(view: View, pageIdx: Int) {
        with(view.context as Activity) {
            val intent = Intent().apply {
                putExtra(Const.KEY_BUNDLE_PAGE_INDEX, pageIdx)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onBindGridViewer(itemList: MutableList<Bitmap?>, position: Int, notify: () -> Unit) {
        val disposable = repository.getThumbnail(core, pdfDocument, position)
            .map {
                it.withBorder(
                    2,
                    ResourcesCompat.getColor(
                        getApplication<Application>().resources,
                        R.color.bg_thumb_border,
                        null
                    )
                )
            }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                itemList[position] = it
                notify.invoke()
            }
        compositeDisposable.add(disposable)
    }
}