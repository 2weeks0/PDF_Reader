package com.ejooyoung.pdf_reader.database.dao

import androidx.room.*
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.database.model.CategoryRelation
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface CategoryRelationDao {

    @Query(
        "SELECT ${Const.DB_CATEGORY_RELATION_COLUMN_BOOK_GUID} FROM ${Const.DB_CATEGORY_RELATION_TABLE} " +
                "WHERE ${Const.DB_CATEGORY_RELATION_COLUMN_CATEGORY_GUID} = :categoryGuid"
    )
    fun selectBookGuidByCategoryGuid(categoryGuid: String): List<String>

    @Query(
        "SELECT count(guid) FROM ${Const.DB_CATEGORY_RELATION_TABLE} " +
                "WHERE ${Const.DB_CATEGORY_RELATION_COLUMN_CATEGORY_GUID} = :categoryGuid"
    )
    fun selectCategoryRelationCount(categoryGuid: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoryRelations(categoryRelationList: List<CategoryRelation>): Completable

    @Delete
    fun deleteCategoryRelation(categoryRelation: CategoryRelation): Completable

    @Query(
        "DELETE FROM ${Const.DB_CATEGORY_RELATION_TABLE} WHERE ${Const.DB_CATEGORY_RELATION_COLUMN_CATEGORY_GUID} = :categoryGuid"
    )
    fun deleteCategoryRelationByCategoryGuid(categoryGuid: String)
}