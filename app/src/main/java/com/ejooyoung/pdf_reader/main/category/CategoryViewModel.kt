package com.ejooyoung.pdf_reader.main.category

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.main.category.model.CategoryItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CategoryViewModel private constructor(
    application: Application,
    private val repository: CategoryRepository
) : BaseAndroidViewModel(application) {

    val itemList = MutableLiveData<List<CategoryItem>>()

    companion object {
        fun newInstance(application: Application, repository: CategoryRepository): CategoryViewModel {
            return CategoryViewModel(application, repository)
        }
    }

    override fun onResume() {
        super.onResume()
        loadCategory()
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
}