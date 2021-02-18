package com.ejooyoung.pdf_reader.main

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.main.model.CurrentCategory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel private constructor(
    application: Application,
    private val repository: MainRepository
) : BaseAndroidViewModel(application) {

    val currentCategory = MutableLiveData<CurrentCategory>()

    companion object {
        fun newInstance(
            application: Application,
            repository: MainRepository
        ): MainViewModel {
            return MainViewModel(application, repository)
        }
    }

    override fun onCreate() {
        super.onCreate()
        loadCurrentCategory()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveCurrentCategory()
    }

    private fun loadCurrentCategory() {
        val disposable = repository.loadCurrentCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                currentCategory.value = it
            }
        compositeDisposable.add(disposable)
    }

    private fun saveCurrentCategory() {
        if (currentCategory.value == null) {
            return
        }

        repository.saveCurrentCategory(currentCategory.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.layAdd -> openFileManager(view.context as Activity)
        }
    }

    private fun openFileManager(activity: Activity) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "application/pdf"
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            putExtra("android.content.extra.SHOW_ADVANCED",true);
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        }

        activity.startActivityForResult(intent, Const.KEY_REQUEST_OPEN_PDF)
    }

    fun insertBookToDB(data: Intent) {
        val disposable = repository.saveBook(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("LEEJY", "insertBookToDB complete")
            }
        compositeDisposable.add(disposable)
    }
}