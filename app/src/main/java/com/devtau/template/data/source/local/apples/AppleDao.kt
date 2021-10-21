package com.devtau.template.data.source.local.apples

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.devtau.template.data.model.Apple
import com.devtau.template.data.source.local.BaseDao
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Database access interface for [Apple] operations
 */
@Dao
interface AppleDao: BaseDao<Apple> {

    //<editor-fold desc="Single object operations">
    @Query("SELECT * FROM Apples WHERE appleId = :id")
    fun getByIdAsFlowable(id: Long): Flowable<Apple>

    @Query("SELECT * FROM Apples WHERE appleId = :id")
    suspend fun getById(id: Long?): Apple?

    @Query("SELECT * FROM Apples WHERE appleId = :id")
    fun observeItem(id: Long?): LiveData<Apple?>
    //</editor-fold>


    //<editor-fold desc="Group operations">
    @Query("SELECT * FROM Apples ORDER BY appleId")
    fun getListAsFlowable(): Flowable<List<Apple>>

    @Query("SELECT * FROM Apples ORDER BY appleId")
    suspend fun getList(): List<Apple>

    @Query("SELECT * FROM Apples ORDER BY appleId")
    fun observeList(): LiveData<List<Apple>>

    @Query("DELETE FROM Apples")
    fun deleteAsync(): Completable

    @Query("DELETE FROM Apples")
    suspend fun deleteAll(): Int
    //</editor-fold>
}