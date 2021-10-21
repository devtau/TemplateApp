package com.devtau.template.data.source.repositories

import androidx.lifecycle.LiveData
import com.devtau.template.data.Result

/**
 * Superclass for all repositories, parameterized by specific model class
 */
interface BaseRepository<T> {

    /**
     * Saves instance of object to local and remote db.
     * Item id is generated locally and item is sent to backend with id assigned
     * @param item object to be saved
     * @return Success with id of saved object or Error
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
     */
    suspend fun deleteItem(item: T)

    /**
     * Reads all instances of stored objects from remote db and updates local db on success
     */
    suspend fun fetchItemsFromBackend()

    /**
     * Reads an instance of stored object from remote db and updates local db on success
     * @param id stored object id
     */
    suspend fun fetchItemFromBackend(id: Long)

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
     */
    suspend fun deleteAll()
}