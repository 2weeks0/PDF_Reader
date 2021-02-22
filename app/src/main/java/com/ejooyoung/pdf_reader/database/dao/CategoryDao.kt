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

    @Query(
        "DELETE FROM ${Const.DB_CATEGORY_TABLE}" +
                " WHERE ${Const.DB_CATEGORY_COLUMN_GUID} = :categoryGuid"
    )
    fun deleteCategory(categoryGuid: String)

    @Query(
        "SELECT CASE WHEN count(${Const.DB_CATEGORY_COLUMN_GUID}) > 0 THEN 1 ELSE 0 END" +
                " FROM ${Const.DB_CATEGORY_TABLE}" +
                " WHERE ${Const.DB_CATEGORY_COLUMN_NAME} = :name"
    )
    fun containCategory(name: String): Flowable<Boolean>

    @Query(
        "UPDATE ${Const.DB_CATEGORY_TABLE} SET ${Const.DB_CATEGORY_COLUMN_NAME} = :categoryName" +
                " WHERE ${Const.DB_CATEGORY_COLUMN_GUID} = :categoryGuid"
    )
    fun updateCategory(categoryGuid: String, categoryName: String): Completable

    @Query(
        "SELECT count(${Const.DB_CATEGORY_COLUMN_GUID}) FROM ${Const.DB_CATEGORY_TABLE}"
    )
    fun selectCount(): Int
}