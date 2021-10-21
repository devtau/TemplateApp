package com.devtau.template.data.source

import androidx.lifecycle.LiveData
import com.devtau.template.R
import com.devtau.template.data.Result
import com.devtau.template.data.Result.Error
import com.devtau.template.data.Result.Success

/**
 * Superclass for all DataSource interfaces, parameterized by specific model class
 */
interface BaseDataSource<T> {

    /**
     * Saves instance of object to local and remote db.
     * Item id is generated locally and item is sent to backend with id assigned
     * @param item object to be saved
     * @return id of saved object
     */
    suspend fun saveItem(item: T): Result<Long>

    /**
     * Reads instance of stored object from local or remote db
     * @param id stored object id
     * @return Success with found object or Error if nothing found
     */
    suspend fun getItem(id: Long): Result<T>

    /**
     * Creates LiveData subscription to observe object by its id
     * @param id stored object id
     */
    fun observeItem(id: Long): LiveData<Result<T?>>

    /**
     * Removes object from local and remote db
     * @param item object instance to be removed
     * @return true if object was removed. else otherwise
     */
    suspend fun deleteItem(item: T): Result<Boolean>

    /**
     * Saves instances of objects to local and remote db
     * @param list objects to be saved
     */
    suspend fun saveList(list: List<T>)

    /**
     * Reads all instances of stored objects from local or remote db
     * @return found objects or empty list if nothing found
     */
    suspend fun getList(): Result<List<T>>

    /**
     * Creates LiveData subscription to observe all objects
     */
    fun observeList(): LiveData<Result<List<T>>>

    /**
     * Removes all object from local and remote db
     * @return Success with removed objects count or Error
     */
    suspend fun deleteAll(): Result<Int>

    fun mapResult(data: T?) = if (data == null) {
        Error(R.string.object_not_found)
    } else {
        Success(data)
    }

    fun mapResult(data: Long?) = if (data == null) {
        Error(R.string.object_not_saved)
    } else {
        Success(data)
    }
}