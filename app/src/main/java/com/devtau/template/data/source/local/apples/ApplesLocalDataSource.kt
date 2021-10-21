package com.devtau.template.data.source.local.apples

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.devtau.template.R
import com.devtau.template.data.model.Apple
import com.devtau.template.data.Result
import com.devtau.template.data.Result.Error
import com.devtau.template.data.Result.Success
import com.devtau.template.data.source.BaseDataSource
import javax.inject.Inject

/**
 * DataSource to work with local db using [Apple] instances
 * @param dao room interface for [Apple] operations
 */
class ApplesLocalDataSource @Inject constructor(
    private val dao: AppleDao
): BaseDataSource<Apple> {

    override suspend fun saveItem(item: Apple): Result<Long> = Success(dao.insert(item))

    override suspend fun getItem(id: Long): Result<Apple> = mapResult(dao.getById(id))

    override fun observeItem(id: Long): LiveData<Result<Apple?>> =
        dao.observeItem(id).map { mapResult(it) }

    override suspend fun deleteItem(item: Apple): Result<Boolean> {
        return if (dao.delete(item) == 1) {
            Success(true)
        } else {
            Error(R.string.object_not_found)
        }
    }

    override suspend fun saveList(list: List<Apple>) = dao.insertList(list)

    override suspend fun getList(): Result<List<Apple>> = try {
        Success(dao.getList())
    } catch (e: Exception) {
        Error(exception = e)
    }

    override fun observeList(): LiveData<Result<List<Apple>>> = dao.observeList().map {
        Success(it)
    }

    override suspend fun deleteAll(): Result<Int> = Success(dao.deleteAll())
}