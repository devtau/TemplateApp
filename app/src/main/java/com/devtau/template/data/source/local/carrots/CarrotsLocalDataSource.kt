package com.devtau.template.data.source.local.carrots

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.devtau.template.R
import com.devtau.template.data.model.Carrot
import com.devtau.template.data.Result
import com.devtau.template.data.Result.Error
import com.devtau.template.data.Result.Success
import com.devtau.template.data.source.BaseDataSource
import javax.inject.Inject

/**
 * DataSource to work with local db using [Carrot] instances
 * @param dao room interface for [Carrot] operations
 */
class CarrotsLocalDataSource @Inject constructor(
    private val dao: CarrotDao
): BaseDataSource<Carrot> {

    override suspend fun saveItem(item: Carrot): Result<Long> = Success(dao.insert(item))

    override suspend fun getItem(id: Long): Result<Carrot> = try {
        mapResult(dao.getById(id))
    } catch (e: Exception) {
        Error(exception = e)
    }

    override fun observeItem(id: Long): LiveData<Result<Carrot?>> = dao.observeItem(id).map { mapResult(it) }

    override suspend fun deleteItem(item: Carrot): Result<Boolean> {
        return if (dao.delete(item) == 1) {
            Success(true)
        } else {
            Error(R.string.object_not_found)
        }
    }

    override suspend fun saveList(list: List<Carrot>) = dao.insertList(list)

    override suspend fun getList(): Result<List<Carrot>> = try {
        Success(dao.getList())
    } catch (e: Exception) {
        Error(exception = e)
    }

    override fun observeList(): LiveData<Result<List<Carrot>>> = dao.observeList().map {
        Success(it)
    }

    override suspend fun deleteAll(): Result<Int> = Success(dao.deleteAll())
}