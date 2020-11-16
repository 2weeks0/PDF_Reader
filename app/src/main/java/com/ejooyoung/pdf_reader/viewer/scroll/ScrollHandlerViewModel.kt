package com.ejooyoung.pdf_reader.viewer.scroll

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ejooyoung.pdf_reader.base.utils.Logger

class ScrollHandlerViewModel(
    application: Application
) : AndroidViewModel(application) {

    companion object {
        fun newInstance(application: Application) = ScrollHandlerViewModel(application)
    }

    fun showInfo() {
        Logger.i()
    }

    fun performUndo() {
        Logger.i()
    }

    fun showContents() {
        Logger.i()
    }

    fun showBookmark() {
        Logger.i()
    }

    fun showSetting() {
        Logger.i()
    }

    override fun onCleared() {
        super.onCleared()
        Logger.i()
    }
}