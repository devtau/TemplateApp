package com.devtau.template.data.source.remote.carrots

import androidx.lifecycle.LiveData
import com.devtau.template.data.Result
import com.devtau.template.data.Result.Success
import com.devtau.template.data.model.Carrot
import com.devtau.template.data.source.BaseDataSource
import javax.inject.Inject

/**
 * DataSource to work with remote db using [Carrot] instances
 * @param backendApi interface of remote db
 */
class CarrotsRemoteDataSource @Inject constructor(
    private val backendApi: CarrotsBackendApi
): BaseDataSource<Carrot> {

    override suspend fun saveItem(item: Carrot): Result<Long> = mapResult(backendApi.saveItem(item).carrot?.id)

    override suspend fun getItem(id: Long): Result<Carrot> = mapResult(backendApi.getItem(id))

    override fun observeItem(id: Long): LiveData<Result<Carrot?>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(item: Carrot): Result<Boolean> = Success(backendApi.deleteItem(item) == 1)

    override suspend fun saveList(list: List<Carrot>) {
        backendApi.saveList(list)
    }

    override suspend fun getList(): Result<List<Carrot>> = Success(backendApi.getList())

    override fun observeList(): LiveData<Result<List<Carrot>>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Int> = Success(backendApi.deleteAll())
}