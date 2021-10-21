package com.devtau.template.data.source.remote.apples

import androidx.lifecycle.LiveData
import com.devtau.template.data.model.Apple
import com.devtau.template.data.source.remote.response.AppleSavedResponse
import com.devtau.template.data.source.remote.response.ApplesListSavedResponse
import retrofit2.http.*

/**
 * Interface of remote db
 */
interface ApplesBackendApi {

    //<editor-fold desc="Single object operations">
    /**
     * Saves instance of object to remote db
     * @param item object to be saved
     * @return response object
     */
    @POST("/apples/new")
    suspend fun saveItem(@Body item: Apple): AppleSavedResponse

    /**
     * Reads instance of stored object from remote db
     * @param id stored object id
     * @return found object or null if nothing found
     */
    @GET("/apples/{id}/apple")
    suspend fun getItem(@Path("id") id: Long): Apple?

    /**
     * Creates LiveData subscription to observe object by its id
     * @param id stored object id
     */
    fun observeItem(id: Long?): LiveData<Apple?>

    /**
     * Removes object from remote db
     * @param item object instance to be removed
     * @return removed objects count
     */
    suspend fun deleteItem(item: Apple): Int
    //</editor-fold>


    //<editor-fold desc="Group operations">
    /**
     * Saves instances of objects to remote db
     * @param list objects to be saved
     */
    @POST("/apples/new_list")
    suspend fun saveList(@Body list: List<Apple>): ApplesListSavedResponse

    /**
     * Reads all instances of stored objects from remote db
     * @return found objects or empty list if nothing found
     */
    @GET("/apples/apples")
    suspend fun getList(): List<Apple>

    /**
     * Creates LiveData subscription to observe all objects
     */
    fun observeList(): LiveData<List<Apple>>

    /**
     * Removes all object from remote db
     * @return removed objects count
     */
    @DELETE("/apples/delete")
    suspend fun deleteAll(): Int
    //</editor-fold>
}