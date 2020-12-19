package com.ejooyoung.pdf_reader

import android.app.Application
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.ejooyoung.pdf_reader.bookshelf.BookshelfViewModel
import com.ejooyoung.pdf_reader.main.MainViewModel
import com.ejooyoung.pdf_reader.base.repository.BookRepositoryImpl
import com.ejooyoung.pdf_reader.base.repository.BookmarkRepositoryImpl
import com.ejooyoung.pdf_reader.base.repository.PdfDocumentRepository
import com.ejooyoung.pdf_reader.bookshelf.BookshelfRepositoryImpl
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.viewer.ViewerRepositoryImpl
import com.ejooyoung.pdf_reader.viewer.ViewerViewModel
import com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.bookmark.BookmarkListViewModel
import com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.contents.ContentsListViewModel
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.ViewerSettingRepositoryImpl
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.ViewerSettingViewModel
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.SettingTouchZoneRepository
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.SettingTouchZoneRepositoryImpl
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.SettingTouchZoneViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactories private constructor(
    private val application: Application,
    owner: SavedStateRegistryOwner,
    private val arg: Any? = null
) : AbstractSavedStateViewModelFactory(owner, null) {

    companion object {
        fun of(
            application: Application,
            owner: SavedStateRegistryOwner,
            defaultArg: Any? = null
        ) = ViewModelFactories(application, owner, defaultArg)
    }

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(BookshelfViewModel::class.java) ->
                BookshelfViewModel.newInstance(application, BookshelfRepositoryImpl.newInstance(application))

            isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel.newInstance(application, BookRepositoryImpl.getInstance(application))

            isAssignableFrom(ViewerViewModel::class.java) ->
                ViewerViewModel.newInstance(application, ViewerRepositoryImpl.newInstance(application), arg as Book)

            isAssignableFrom(BookmarkListViewModel::class.java) ->
                BookmarkListViewModel.newInstance(application, BookmarkRepositoryImpl.getInstance(application), arg as Book)

            isAssignableFrom(ContentsListViewModel::class.java) ->
                ContentsListViewModel.newInstance(application, arg as PdfDocumentRepository)

            isAssignableFrom(ViewerSettingViewModel::class.java) ->
                ViewerSettingViewModel.newInstance(application, ViewerSettingRepositoryImpl.newInstance(application))

            isAssignableFrom(SettingTouchZoneViewModel::class.java) ->
                SettingTouchZoneViewModel.newInstance(application, SettingTouchZoneRepositoryImpl.newInstance(application))

            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}
