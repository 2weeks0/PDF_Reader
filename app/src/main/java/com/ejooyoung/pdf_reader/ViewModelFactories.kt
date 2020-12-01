package com.ejooyoung.pdf_reader

import android.app.Application
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.ejooyoung.pdf_reader.bookshelf.BookshelfViewModel
import com.ejooyoung.pdf_reader.main.SettingViewModel
import com.ejooyoung.pdf_reader.base.repository.BookRepositoryImpl
import com.ejooyoung.pdf_reader.bookshelf.BookshelfRepositoryImpl
import com.ejooyoung.pdf_reader.model.Book
import com.ejooyoung.pdf_reader.viewer.ViewerViewModel
import com.ejooyoung.pdf_reader.viewer.menu.ContentsViewModel

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

            isAssignableFrom(SettingViewModel::class.java) ->
                SettingViewModel.newInstance(application, BookRepositoryImpl.getInstance(application))

            isAssignableFrom(ViewerViewModel::class.java) ->
                ViewerViewModel.newInstance(application, BookRepositoryImpl.getInstance(application), arg as Book)

            isAssignableFrom(ContentsViewModel::class.java) ->
                ContentsViewModel.newInstance(application, arg as Book)

            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}
