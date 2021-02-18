package com.ejooyoung.pdf_reader.main

import android.content.Intent
import com.ejooyoung.pdf_reader.application.MainApplication
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.ext.toBookList
import com.ejooyoung.pdf_reader.base.repository.BookRepositoryImpl
import com.ejooyoung.pdf_reader.main.model.CurrentCategory
import com.ejooyoung.pdf_reader.main.model.CurrentCategoryType
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

class MainRepositoryImpl private constructor(
    private val mainApplication: MainApplication
): MainRepository {

    // 테스트용으로 book 추가하는 로직을 main 에다가 넣어놓음
    // 오른쪽 panel 인 setting 쪽 작업할 때, 빼야함 (saveBook 도)
    private val bookRepository = BookRepositoryImpl.getInstance(mainApplication)

    companion object {
        const val DEFAULT_VALUE = "";


        fun newInstance(mainApplication: MainApplication): MainRepository {
            return MainRepositoryImpl(mainApplication)
        }
    }

    override fun loadCurrentCategory(): Flowable<CurrentCategory> {
        return Flowable.fromCallable {
            val type = mainApplication.getPreference(Const.PREF_RECENT_CATEGORY_TYPE, CurrentCategoryType.ALL.index)
            val guid = mainApplication.getPreference(Const.PREF_RECENT_CATEGORY_GUID, DEFAULT_VALUE)
            val name = mainApplication.getPreference(Const.PREF_RECENT_CATEGORY_NAME, DEFAULT_VALUE)
            CurrentCategory.valueOf(CurrentCategoryType.indexOf(type), guid, name)
        }
    }

    override fun saveCurrentCategory(currentCategory: CurrentCategory): Completable {
        return Completable.fromAction {
            mainApplication.putPreference(Const.PREF_RECENT_CATEGORY_TYPE, currentCategory.type.index)
            if (currentCategory.type == CurrentCategoryType.CUSTOM) {
                mainApplication.putPreference(Const.PREF_RECENT_CATEGORY_GUID, currentCategory.guid!!)
                mainApplication.putPreference(Const.PREF_RECENT_CATEGORY_NAME, currentCategory.name!!)
            }
        }
    }

    override fun saveBook(data: Intent): Completable {
        return Observable.fromCallable { data.toBookList(mainApplication).toTypedArray() }
            .flatMapCompletable { bookRepository.insertBooks(*it) }
    }
}