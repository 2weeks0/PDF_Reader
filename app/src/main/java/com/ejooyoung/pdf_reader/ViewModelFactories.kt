package com.ejooyoung.pdf_reader

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.ejooyoung.pdf_reader.bookshelf.BookshelfViewModel
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.main.SettingViewModel
import com.ejooyoung.pdf_reader.repository.BookRepositoryImpl

@Suppress("UNCHECKED_CAST")
class ViewModelFactories private constructor(
    private val application: Application,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    companion object {
        fun of(
            application: Application,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null
        ) = ViewModelFactories(application, owner, defaultArgs)
    }

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(BookshelfViewModel::class.java) -> BookshelfViewModel.newInstance(application)

            isAssignableFrom(SettingViewModel::class.java) ->
                with(DatabaseProvider.provideBookSource(application)) {
                    SettingViewModel.newInstance(application, BookRepositoryImpl.getInstance(this))
                }

            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}
