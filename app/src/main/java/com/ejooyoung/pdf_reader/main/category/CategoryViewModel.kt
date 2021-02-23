package com.ejooyoung.pdf_reader.main.category

import android.app.Application
import android.app.Dialog
import android.view.View
import androidx.databinding.ObservableInt
import androidx.fragment.app.findFragment
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.dialog.InputTextDialogFactory
import com.ejooyoung.pdf_reader.base.dialog.MenuDialog
import com.ejooyoung.pdf_reader.base.ext.makeToast
import com.ejooyoung.pdf_reader.base.ext.startSettingCategoryActivity
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.main.CurrentCategoryOwner
import com.ejooyoung.pdf_reader.main.MainActivity
import com.ejooyoung.pdf_reader.main.category.listener.OnClickCategoryListener
import com.ejooyoung.pdf_reader.main.category.model.CategoryItem
import com.ejooyoung.pdf_reader.main.model.CurrentCategory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CategoryViewModel private constructor(
    application: Application,
    private val repository: CategoryRepository
) : BaseAndroidViewModel(application), OnClickCategoryListener {

    val itemList = MutableLiveData<List<CategoryItem>>()
    val countOfAllBook = ObservableInt()
    val countOfFavoriteBooks = ObservableInt()
    var touchPosX: Float = 0f
    var touchPosY: Float = 0f

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

    override fun onTouchCategory(touchPosX: Float, touchPosY: Float) {
        this.touchPosX = touchPosX
        this.touchPosY = touchPosY
    }

    override fun onClickCategory(view: View, currentCategory: CurrentCategory) {
        (view.context as? CurrentCategoryOwner)?.getCurrentCategory()?.value = currentCategory
        (view.context as? MainActivity)?.closePanels()
    }

    override fun onLongClickCategory(view: View, categoryItem: CategoryItem): Boolean {
        MenuDialog.Factory(view.context, touchPosX, touchPosY + view.height * 2 + view.y)
            .setItems(R.layout.item_menu_dialog, R.array.MENU_SETTING_CATEGORY) { v: View, i: Int ->
                when (i) {
                    0 -> onChangeName(v, categoryItem)
                    1 -> onChangeName(v, categoryItem)
                    2 -> onDeleteItem(categoryItem)
                }
            }
            .show()
        return true
    }

    fun startSettingCategoryActivity(view: View) {
        view.findFragment<CategoryFragment>().startSettingCategoryActivity()
    }

    private fun onChangeName(view: View, categoryItem: CategoryItem) {
        InputTextDialogFactory(view.context)
            .setTitle(R.string.txt_change_category_name)
            .setInitialText(categoryItem.name)
            .setConfirmClickListener { dialog: Dialog, name: String ->
                changeName(dialog, categoryItem.guid, name)
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

    private fun onDeleteItem(categoryItem: CategoryItem) {
        val disposable = repository.deleteCategory(categoryItem.guid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        compositeDisposable.add(disposable)
    }
}