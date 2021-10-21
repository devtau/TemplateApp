package com.devtau.template.data.source.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

/**
 * Superclass for all database access interfaces for room local storage.
 * Contains most common CRUD operations
 */
interface BaseDao<T> {

    /**
     * Asynchronously saves [item] to local db using coroutine
     * @return id of saved object
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: T?): Long

    /**
     * Asynchronously removes [item] from local db using coroutine
     * @return count of objects removed
     */
    @Delete
    suspend fun delete(item: T?): Int

    /**
     * Saves instances of objects to local db using coroutine
     * @param list objects to be saved
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<T?>?)
}