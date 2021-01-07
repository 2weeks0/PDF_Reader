package com.ejooyoung.pdf_reader.viewer

import android.app.Application
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.findFragment
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference
import com.ejooyoung.pdf_reader.application.preference.ViewerPreferenceMap
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.ext.*
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.base.utils.DateUtils
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.database.model.Bookmark
import com.ejooyoung.pdf_reader.viewer.scrollhandler.grid.GridViewerActivity
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZone
import com.github.barteksc.pdfviewer.PDFView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ViewerViewModel private constructor(
    application: Application,
    private val viewerRepository: ViewerRepository,
    val book: Book
) : BaseAndroidViewModel(application), ViewerMenuClickListener {

    val touchZone = TouchZone.of()
    val visibilityScrollHandler = ObservableBoolean(false)
    val currentPage = MutableLiveData<Int>(book.currentPage)
    val isBookmarkedPage = ObservableBoolean(false)
    val preferenceMap: MutableLiveData<ViewerPreferenceMap> = MutableLiveData()

    companion object {
        fun newInstance(
            application: Application,
            viewerRepository: ViewerRepository,
            book: Book
        ) = ViewerViewModel(application, viewerRepository, book)
    }

    init {
        updateReadTime()
    }

    override fun onResume() {
        super.onResume()
        loadPreference()
        loadTouchZonePreference()
    }

    override fun onDestroy() {
        super.onDestroy()
        updateBook()
    }

    private fun loadPreference() {
        DevLogger.i()
        loadDisposable?.dispose()
        visibilityOfProgressBar.set(true)
        loadDisposable = viewerRepository.loadAllPreference()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                visibilityOfProgressBar.set(false)
                preferenceMap.value = it
            }
    }

    private fun loadTouchZonePreference() {
        val disposable = viewerRepository.loadTouchZonePreference(touchZone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        compositeDisposable.add(disposable)
    }

    private fun updateReadTime() {
        book.readTime = DateUtils.getCurrentTimeToDate()
    }

    private fun updateBook() {
        book.currentPage = currentPage.value!!
        viewerRepository.updateBook(book)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!
            .subscribe()
    }

    fun updateIsBookmarkedPage() {
        val disposable = viewerRepository.isBookmarkedPage(book.guid, currentPage.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    DevLogger.d("isBookmarkedPage: $it")
                    isBookmarkedPage.set(it)
                }
        compositeDisposable.add(disposable)
    }

    override fun previousPage(pdfView: PDFView) {
        DevLogger.w("currentPage.value: ${currentPage.value!!}")
        val target = currentPage.value!! - 1
        if (target < 0) {
            return
        }
        currentPage.value = target
        pdfView.jumpTo(target, true)
    }

    override fun nextPage(pdfView: PDFView) {
        DevLogger.w("currentPage.value: ${currentPage.value!!}")
        val target = currentPage.value!! + 1
        if (target >= pdfView.pageCount) {
            return
        }
        currentPage.value = target
        pdfView.jumpTo(target, true)
    }

    override fun showInfo(view: View) {
        DevLogger.i()
    }

    override fun showGrid(view: View) {
        val intent = Intent(view.context, GridViewerActivity::class.java).apply {
            putExtra(Const.KEY_BUNDLE_BOOK, book)
        }
        view.findFragment<ViewerFragment>().startActivity(intent)
    }

    override fun showContents(view: View) {
        DevLogger.i()
        view.findFragment<ViewerFragment>().startContentsActivity(book)
    }

    override fun addBookmark(view: View) {
        val bookmark = Bookmark.valueOf(
            getApplication<Application>().resources.getString(R.string.txt_unknown_name),
            currentPage.value!!,
            book.guid
        )
        val disposable = viewerRepository.insertBookmark(bookmark)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                isBookmarkedPage.set(true)
                makeSnack(view, R.string.msg_toast_add_bookmark, R.string.btn_change) {
                    view.findFragment<ViewerFragment>().startRenameActivity(
                        R.string.txt_rename_bookmark,
                        bookmark
                    )
                }
            }
        compositeDisposable.add(disposable)
    }

    override fun deleteBookmark(view: View) {
        val disposable = viewerRepository.deleteBookmark(currentPage.value!!, book.guid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                isBookmarkedPage.set(false)
                makeToast(R.string.msg_toast_del_bookmark)
            }
        compositeDisposable.add(disposable)
    }

    override fun showSetting(view: View) {
        view.findFragment<ViewerFragment>().startViewerSettingActivity()
    }

    override fun onCleared() {
        super.onCleared()
        DevLogger.i()
    }

    fun updateRenamedBookmark(bookmark: Bookmark) {
        val disposable = viewerRepository.updateBookmark(bookmark)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                makeToast(R.string.msg_success_rename_bookmark)
            }
        compositeDisposable.add(disposable)
    }

    fun onVolumeKeyDown(pdfView: PDFView, keyCode: Int): Boolean {
        if (preferenceMap.value!![ViewerPreference.JUMP_BY_VOLUME_KEY]) {
            when (keyCode) {
                KeyEvent.KEYCODE_VOLUME_UP -> previousPage(pdfView)
                KeyEvent.KEYCODE_VOLUME_DOWN -> nextPage(pdfView)
            }
            return true
        }
        return false
    }
}