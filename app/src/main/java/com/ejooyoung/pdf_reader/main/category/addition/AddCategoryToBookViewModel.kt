package com.ejooyoung.pdf_reader.main.category.addition

import android.app.Activity
import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.main.category.addition.listener.OnClickAddableBookListener
import com.ejooyoung.pdf_reader.main.category.addition.model.AddableBook
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class AddCategoryToBookViewModel private constructor(
    application: Application,
    private val repository: AddCategoryToBookRepository,
    private val categoryGuid: String
) : BaseAndroidViewModel(application), OnClickAddableBookListener {

    val itemList = MutableLiveData<List<AddableBook>>()
    val selectedItemCount = ObservableInt()

    companion object {
        fun newInstance(
            application: Application,
            repository: AddCategoryToBookRepository,
            categoryGuid: String
        ): AddCategoryToBookViewModel {
            return AddCategoryToBookViewModel(application, repository, categoryGuid)
        }
    }

    override fun onCreate() {
        super.onCreate()
        loadAddableBookList()
    }

    private fun loadAddableBookList() {
        visibilityOfProgressBar.set(true)
        val disposable = repository.loadAddableBookList(categoryGuid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                visibilityOfProgressBar.set(false)
                itemList.value = it
            }
        compositeDisposable.add(disposable)
    }

    fun onClickSave(view: View) {
        val selectedAddableBookList = itemList.value!!.filter { it.selected.get() }
        val disposable = repository.saveAddToCategory(categoryGuid, selectedAddableBookList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                with(view) {
                    Toast.makeText(
                        context,
                        resources.getString(R.string.txt_toast_add_book_category),
                        Toast.LENGTH_SHORT
                    ).show()
                    (context as Activity).finish()
                }
            }
        compositeDisposable.add(disposable)
    }

    override fun onClickAddableBook(addableBook: AddableBook) {
        addableBook.setSelected(!addableBook.selected.get())
        selectedItemCount.set(itemList.value!!.count { it.selected.get() })
    }
}