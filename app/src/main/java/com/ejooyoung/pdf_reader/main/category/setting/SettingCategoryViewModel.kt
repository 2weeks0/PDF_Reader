package com.ejooyoung.pdf_reader.main.category.setting

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SettingCategoryViewModel private constructor(
    application: Application,
    private val repository: SettingCategoryRepository
): BaseAndroidViewModel(application) {

    val itemList = MutableLiveData<List<SettingCategoryItem>>()

    companion object {
        fun newInstance(
            application: Application,
            repository: SettingCategoryRepository
        ): SettingCategoryViewModel {
            return SettingCategoryViewModel(application, repository)
        }
    }

    override fun onCreateView() {
        super.onCreateView()
        loadCategory()
    }

    private fun loadCategory() {
        visibilityOfProgressBar.set(true)
        val disposable = repository.selectAllCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                itemList.value = it
                visibilityOfProgressBar.set(false)
            }
        compositeDisposable.add(disposable)
    }
}