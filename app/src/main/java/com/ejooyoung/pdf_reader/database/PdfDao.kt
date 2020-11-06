package com.ejooyoung.pdf_reader.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ejooyoung.pdf_reader.model.MyPDF
import com.ejooyoung.pdf_reader.util.Const
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


@Dao
interface PdfDao {

    @Query("SELECT * FROM ${Const.DB.TABLE_NAME_PDF}")
    fun selectAllPdf(): List<MyPDF>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPdf(vararg myPDF: MyPDF)

    @Delete
    fun deletePdf(myPDF: MyPDF)
}