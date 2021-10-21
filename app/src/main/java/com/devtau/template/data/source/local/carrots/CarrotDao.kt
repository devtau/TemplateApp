package com.devtau.template.data.source.local.carrots

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.devtau.template.data.model.Carrot
import com.devtau.template.data.source.local.BaseDao
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Database access interface for [Carrot] instances
 */
@Dao
interface CarrotDao: BaseDao<Carrot> {

    //<editor-fold desc="Single object operations">
    @Query("SELECT * FROM Carrots WHERE carrotId = :id")
    fun getByIdAsFlowable(id: Long): Flowable<Carrot>

    @Query("SELECT * FROM Carrots WHERE carrotId = :id")
    suspend fun getById(id: Long?): Carrot?

    @Query("SELECT * FROM Carrots WHERE carrotId = :id")
    fun observeItem(id: Long?): LiveData<Carrot?>
    //</editor-fold>


    //<editor-fold desc="Group operations">
    @Query("SELECT * FROM Carrots ORDER BY carrotId")
    fun getListAsFlowable(): Flowable<List<Carrot>>

    @Query("SELECT * FROM Carrots ORDER BY carrotId")
    suspend fun getList(): List<Carrot>

    @Query("SELECT * FROM Carrots ORDER BY carrotId")
    fun observeList(): LiveData<List<Carrot>>

    @Query("DELETE FROM Carrots")
    fun deleteAsync(): Completable

    @Query("DELETE FROM Carrots")
    suspend fun deleteAll(): Int
    //</editor-fold>
}