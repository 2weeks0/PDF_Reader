package com.ejooyoung.pdf_reader.main.category.setting

import android.app.Application
import android.app.Dialog
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.dialog.InputTextDialogFactory
import com.ejooyoung.pdf_reader.base.ext.makeToast
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.main.category.setting.listener.ItemTouchListener
import com.ejooyoung.pdf_reader.main.category.setting.listener.MenuDialogItemClickListener
import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SettingCategoryViewModel private constructor(
    application: Application,
    private val repository: SettingCategoryRepository
) : BaseAndroidViewModel(application), MenuDialogItemClickListener {

    val itemList = MutableLiveData<List<SettingCategoryItem>>()
    val checkedItemCount = ObservableInt(-1)
    val itemTouchListener = ItemTouchListener.newInstance(this, checkedItemCount)
    val editMode = ObservableBoolean(false)

    companion object {
        fun newInstance(
            application: Application,
            repository: SettingCategoryRepository
        ): SettingCategoryViewModel {
            return SettingCategoryViewModel(application, repository)
        }
    }

    override fun onCreate() {
        super.onCreate()
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
                checkedItemCount.set(0)
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
                if (it) {
                    makeToast(R.string.txt_success_add_category)
                    dialog.dismiss()
                }
                else {
                    makeToast(R.string.txt_already_has_category)
                }
            }
        compositeDisposable.add(disposable)
    }

    override fun onChangeName(view: View, item: SettingCategoryItem) {
        InputTextDialogFactory(view.context)
            .setTitle(R.string.txt_change_category_name)
            .setInitialText(item.name)
            .setConfirmClickListener { dialog: Dialog, name: String ->
                changeName(dialog, item.guid, name)
            }
            .show()
    }

    private fun changeName(dialog: Dialog, categoryGuid: String, changedName: String) {
        val disposable = repository.updateCategory(categoryGuid, changedName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it) {
                    makeToast(R.string.txt_success_change_category_name)
                    dialog.dismiss()
                }
                else {
                    makeToast(R.string.txt_already_has_category)
                }
            }
        compositeDisposable.add(disposable)
    }

    override fun onDeleteItem(item: SettingCategoryItem) {
        val disposable = repository.deleteCategory(item.guid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        compositeDisposable.add(disposable)
    }

    override fun onStartEditMode() {
        itemList.value?.let {
            it.asSequence().forEach { item -> item.editMode.set(true) }
            editMode.set(true)
        }
    }

    fun onBackPressed(): Boolean {
        if (editMode.get()) {
            finishEditMode()
            return true
        }
        return false
    }

    private fun finishEditMode() {
        itemList.value!!.asSequence().forEach { item ->
            item.editMode.set(false)
            item.selected.set(false)
        }
        editMode.set(false)
        checkedItemCount.set(0)
    }

    fun onClickCheckAll() {
        val checkedAll = checkedItemCount.get() == itemList.value!!.size
        itemList.value!!.forEach { it.selected.set(!checkedAll) }
        checkedItemCount.set(itemList.value!!.count { it.selected.get() })
    }

    fun changeSelectedItemName(view: View) {
        itemList.value?.asSequence()
            ?.find { it.selected.get() }
            ?.let { onChangeName(view, it) }
    }

    fun deleteSelectedItem() {
        itemList.value?.asSequence()
            ?.filter { it.selected.get() }
            ?.toList()
            ?.forEach { onDeleteItem(it) }
    }
}