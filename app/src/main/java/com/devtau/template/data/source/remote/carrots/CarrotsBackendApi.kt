package com.devtau.template.data.source.remote.carrots

import androidx.lifecycle.LiveData
import com.devtau.template.data.Result
import com.devtau.template.data.model.Apple
import com.devtau.template.data.model.Carrot
import com.devtau.template.data.source.remote.response.CarrotSavedResponse
import com.devtau.template.data.source.remote.response.CarrotsListSavedResponse
import retrofit2.http.*

/**
 * Interface of remote db for [Carrot]
 */
interface CarrotsBackendApi {

    //<editor-fold desc="Single object operations">
    /**
     * Saves instance of object to remote db
     * @param item object to be saved
     * @return response object
     */
    @POST("/carrots/new")
    suspend fun saveItem(@Body item: Carrot): CarrotSavedResponse

    /**
     * Reads instance of stored object from remote db
     * @param id stored object id
     * @return found object or null if nothing found
     */
    @GET("/carrot/{id}/carrot")
    suspend fun getItem(@Path("id") id: Long): Carrot?

    /**
     * Creates LiveData subscription to observe object by its id
     * @param id stored object id
     */
    fun observeItem(id: Long?): LiveData<Carrot?>

    /**
     * Removes object from remote db
     * @param item object instance to be removed
     * @return removed objects count
     */
    suspend fun deleteItem(item: Carrot): Int
    //</editor-fold>


    //<editor-fold desc="Group operations">
    /**
     * Saves instances of objects to remote db
     * @param list objects to be saved
     */
    @POST("/carrots/new_list")
    suspend fun saveList(@Body list: List<Carrot>): CarrotsListSavedResponse

    /**
     * Reads all instances of stored objects from remote db
     * @return found objects or empty list if nothing found
     */
    @GET("/carrots/carrots")
    suspend fun getList(): List<Carrot>

    /**
     * Creates LiveData subscription to observe all objects
     */
    fun observeList(): LiveData<List<Carrot>>

    /**
     * Removes all object from remote db
     * @return removed objects count
     */
    @DELETE("/carrots/delete")
    suspend fun deleteAll(): Int
    //</editor-fold>
}