package com.devtau.template.data.source.repositories

import com.devtau.template.data.Result
import com.devtau.template.data.Result.Error
import com.devtau.template.data.Result.Success
import com.devtau.template.data.model.Carrot
import com.devtau.template.data.source.local.carrots.CarrotsLocalDataSource
import com.devtau.template.data.source.remote.carrots.CarrotsRemoteDataSource
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Repository to work with [Carrot] instances
 * @param local dataSource to work with local db using [Carrot] instances
 * @param remote dataSource to work with remote db using [Carrot] instances
 */
class CarrotsRepository @Inject constructor(
    private val local: CarrotsLocalDataSource,
    private val remote: CarrotsRemoteDataSource
): BaseRepository<Carrot> {

    override suspend fun saveItem(item: Carrot): Result<Long> {
        val localId = local.saveItem(item)
        when (localId) {
            is Success -> {
                item.id = localId.data
                remote.saveItem(item)
            }
            is Error -> throw localId.exception!!
        }
        return localId
    }

    override suspend fun getItem(id: Long): Result<Carrot> = local.getItem(id)

    override fun observeItem(id: Long) = local.observeItem(id)

    override suspend fun deleteItem(item: Carrot) {
        coroutineScope {
            launch { local.deleteItem(item) }
            //TODO: uncomment after server setup
//            launch { remote?.deleteItem(item) }
        }
    }

    override suspend fun fetchItemsFromBackend() {
        when (val remoteList = remote.getList()) {
            is Success -> {
                local.deleteAll()
                remoteList.data.forEach { local.saveItem(it) }
            }
            is Error -> throw remoteList.exception!!
        }
    }

    override suspend fun fetchItemFromBackend(id: Long) {
        when (val remoteItem = remote.getItem(id)) {
            is Success -> local.saveItem(remoteItem.data)
            is Error -> throw remoteItem.exception!!
        }
    }

    override suspend fun saveList(list: List<Carrot>) {
        Timber.d("saveList size=${list.size}")
        coroutineScope {
            launch { local.saveList(list) }
            //TODO: uncomment after server setup
//            launch { remote?.saveList(list) }
        }
    }

    override suspend fun getList(): Result<List<Carrot>> = local.getList()

    override fun observeList() = local.observeList()

    override suspend fun deleteAll() {
        coroutineScope {
            launch { local.deleteAll() }
            //TODO: uncomment after server setup
//           launch { remote?.deleteAll() }
        }
    }
}