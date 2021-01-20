package com.ejooyoung.pdf_reader.main.category.setting

import android.app.Application
import android.app.Dialog
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.dialog.InputTextDialogFactory
import com.ejooyoung.pdf_reader.base.ext.makeToast
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SettingCategoryViewModel private constructor(
    application: Application,
    private val repository: SettingCategoryRepository
) : BaseAndroidViewModel(application) {

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

    fun showInputCategoryDialog(view: View) {
        InputTextDialogFactory(view.context)
            .setTitle(R.string.txt_add_category)
            .setConfirmClickListener { dialog: Dialog, name: String ->
                saveCategory(dialog, name)
            }
            .show()
    }

    private fun saveCategory(dialog: Dialog, categoryName: String) {
        val disposable = repository.saveCategory(categoryName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                DevLogger.w("success: $it")
                if (it) {
                    makeToast(R.string.txt_success_add_category)
                    dialog.dismiss()
                }
                else {
                    makeToast(R.string.txt_already_has_category)
                }
            }
        compositeDisposable.add(disposable)
        loadCategory()
    }
}