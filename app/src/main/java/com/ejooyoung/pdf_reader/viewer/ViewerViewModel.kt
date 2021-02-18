package com.ejooyoung.pdf_reader.viewer

import android.app.Application
import android.graphics.Bitmap
import android.view.KeyEvent
import android.view.View
import android.widget.SeekBar
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.findFragment
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference
import com.ejooyoung.pdf_reader.application.preference.ViewerPreferenceMap
import com.ejooyoung.pdf_reader.base.ext.*
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.base.utils.DateUtils
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.database.model.Bookmark
import com.ejooyoung.pdf_reader.viewer.listener.PreviewListener
import com.ejooyoung.pdf_reader.viewer.listener.ViewerMenuClickListener
import com.ejooyoung.pdf_reader.viewer.manager.PDFManager
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZone
import com.github.barteksc.pdfviewer.PDFView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ViewerViewModel private constructor(
    application: Application,
    private val viewerRepository: ViewerRepository,
    val book: Book
) : BaseAndroidViewModel(application),
    ViewerMenuClickListener, PreviewListener {

    val touchZone = TouchZone.of()
    val visibilityScrollHandler = ObservableBoolean(false)
    val visibilityPreview = ObservableBoolean(false)
    val currentPage = MutableLiveData<Int>(book.currentPage)
    val isBookmarkedPage = ObservableBoolean(false)
    val preferenceMap: MutableLiveData<ViewerPreferenceMap> = MutableLiveData()
    val previewThumbnail: MutableLiveData<Bitmap> = MutableLiveData()
    private val pdfManager = PDFManager.getInstance(getApplication())

    companion object {
        fun newInstance(
            application: Application,
            viewerRepository: ViewerRepository,
            book: Book
        ) = ViewerViewModel(application, viewerRepository, book)
    }

    override fun onCreate() {
        super.onCreate()
        updateReadTime()
        pdfManager.openPdfDocument(getApplication(), book)
    }

    override fun onResume() {
        super.onResume()
        loadPreference()
        loadTouchZonePreference()
    }

    override fun onPause() {
        super.onPause()
        updateBook()
    }

    override fun onDestroy() {
        super.onDestroy()
        pdfManager.closePdfDocument()
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
        DevLogger.i()
        view.findFragment<ViewerFragment>().startGridViewerActivity(book)
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

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        DevLogger.d("progress: ${seekBar.progress}")
        loadPreview(progress)
    }

    override fun onStartTracking(seekBar: SeekBar) {
        DevLogger.w("progress: ${seekBar.progress}")
        visibilityPreview.set(true)
        loadPreview(seekBar.progress)
    }

    override fun onStopTracking(seekBar: SeekBar, pdfView: PDFView) {
        DevLogger.d("progress: ${seekBar.progress}")
        visibilityPreview.set(false)
        if (currentPage.value != seekBar.progress) {
            pdfView.jumpTo(seekBar.progress)
        }
    }

    private fun loadPreview(index: Int) {
        if (pdfManager.getThumbCached(index) != null) {
            previewThumbnail.value = pdfManager.getThumbCached(index)
        }
        else if (!pdfManager.isStartLoad(index)) {
            pdfManager.startLoad(index)
            val disposable = viewerRepository.loadThumbnail(getApplication(), index)
                .map { it.withBorder(getApplication<Application>().resources) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    pdfManager.putThumbCached(index, it)
                    if (pdfManager.previewIndex == index) {
                        previewThumbnail.value = it
                    }
                }
            compositeDisposable.add(disposable)
        }
    }
}