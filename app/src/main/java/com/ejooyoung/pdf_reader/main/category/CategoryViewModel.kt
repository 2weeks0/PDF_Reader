package com.ejooyoung.pdf_reader.main.category

import android.app.Application
import android.view.View
import androidx.databinding.ObservableInt
import androidx.fragment.app.findFragment
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.base.ext.startSettingCategoryActivity
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.main.CurrentCategoryOwner
import com.ejooyoung.pdf_reader.main.MainActivity
import com.ejooyoung.pdf_reader.main.category.listener.OnClickCategoryListener
import com.ejooyoung.pdf_reader.main.category.model.CategoryItem
import com.ejooyoung.pdf_reader.main.model.CurrentCategory
import com.ejooyoung.pdf_reader.main.model.CurrentCategoryType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CategoryViewModel private constructor(
    application: Application,
    private val repository: CategoryRepository
) : BaseAndroidViewModel(application), OnClickCategoryListener {

    val itemList = MutableLiveData<List<CategoryItem>>()
    val countOfAllBook = ObservableInt()
    val countOfFavoriteBooks = ObservableInt()

    companion object {
        fun newInstance(
            application: Application,
            repository: CategoryRepository
        ): CategoryViewModel {
            return CategoryViewModel(application, repository)
        }
    }

    override fun onCreate() {
        super.onCreate()
        loadCategory()
        loadCountOfAllBook()
        loadCountOfFavoriteBooks()
    }

    private fun loadCategory() {
        visibilityOfProgressBar.set(true)
        val disposable = repository.loadCategoryItem()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                itemList.value = it
                visibilityOfProgressBar.set(false)
            }
        compositeDisposable.add(disposable)
    }

    private fun loadCountOfAllBook() {
        val disposable = repository.loadCountOfAllBook()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                countOfAllBook.set(it)
            }
        compositeDisposable.add(disposable)
    }

    private fun loadCountOfFavoriteBooks() {
        val disposable = repository.loadCountOfFavoriteBook()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                countOfFavoriteBooks.set(it)
            }
        compositeDisposable.add(disposable)
    }

    override fun onClickCategory(view: View, currentCategory: CurrentCategory) {
        DevLogger.i()
        (view.context as? CurrentCategoryOwner)?.getCurrentCategory()?.value = currentCategory
        (view.context as? MainActivity)?.closePanels()
    }

    fun startSettingCategoryActivity(view: View) {
        view.findFragment<CategoryFragment>().startSettingCategoryActivity()
    }
}