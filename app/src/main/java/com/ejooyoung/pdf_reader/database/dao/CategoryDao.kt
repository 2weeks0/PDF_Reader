package com.ejooyoung.pdf_reader.database.dao

import androidx.room.*
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.database.model.Category
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface CategoryDao {

    @Query(
        "SELECT * FROM ${Const.DB_CATEGORY_TABLE}" +
                " ORDER BY ${Const.DB_CATEGORY_COLUMN_NAME} COLLATE LOCALIZED ASC"
    )
    fun selectAllCategory(): Flowable<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: Category): Completable

    @Delete
    fun deleteCategory(category: Category): Completable
}