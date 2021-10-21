package com.devtau.template.data.source.remote.apples

import androidx.lifecycle.LiveData
import com.devtau.template.data.Result
import com.devtau.template.data.Result.Success
import com.devtau.template.data.model.Apple
import com.devtau.template.data.source.BaseDataSource
import javax.inject.Inject

/**
 * DataSource to work with remote db using [Apple] instances
 * @param backendApi interface of remote db
 */
class ApplesRemoteDataSource @Inject constructor(
    private val backendApi: ApplesBackendApi
): BaseDataSource<Apple> {

    override suspend fun saveItem(item: Apple): Result<Long> = mapResult(backendApi.saveItem(item).apple?.id)

    override suspend fun getItem(id: Long): Result<Apple> = mapResult(backendApi.getItem(id))

    override fun observeItem(id: Long): LiveData<Result<Apple?>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(item: Apple): Result<Boolean> = Success(backendApi.deleteItem(item) == 1)

    override suspend fun saveList(list: List<Apple>) {
        backendApi.saveList(list)
    }

    override suspend fun getList(): Result<List<Apple>> = Success(backendApi.getList())

    override fun observeList(): LiveData<Result<List<Apple>>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Int> = Success(backendApi.deleteAll())
}