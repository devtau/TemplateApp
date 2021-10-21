package com.devtau.template.data.source.repositories

import com.devtau.template.data.Result
import com.devtau.template.data.Result.Error
import com.devtau.template.data.Result.Success
import com.devtau.template.data.model.Apple
import com.devtau.template.data.source.local.apples.ApplesLocalDataSource
import com.devtau.template.data.source.remote.apples.ApplesRemoteDataSource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Repository to work with [Apple] instances
 * @param local dataSource to work with local db using [Apple] instances
 * @param remote dataSource to work with remote db using [Apple] instances
 */
class ApplesRepository @Inject constructor(
    private val local: ApplesLocalDataSource,
    private val remote: ApplesRemoteDataSource
): BaseRepository<Apple> {

    override suspend fun saveItem(item: Apple): Result<Long> {
        val localId = local.saveItem(item)
        when (localId) {
            is Success -> {
                item.id = localId.data
                item.id = localId.data
                item.id = localId.data
//                remote.saveItem(item)
            }
            is Error -> throw localId.exception!!
        }
        return localId
    }

    override suspend fun getItem(id: Long): Result<Apple> = local.getItem(id)

    override fun observeItem(id: Long) = local.observeItem(id)

    override suspend fun deleteItem(item: Apple) {
        coroutineScope {
            launch { local.deleteItem(item) }
            //TODO: uncomment after server setup
//            launch { remote?.deleteItem(item) }
        }
    }

    override suspend fun fetchItemsFromBackend() {
        if ((local.getList() as? Success)?.data?.isEmpty() == true) {
            local.saveList(Apple.getMockLocalList())
        }

//        when (val remoteList = remote.getList()) {
//            is Success -> {
//                local.deleteAll()
//                local.saveList(remoteList.data)
//            }
//            is Error -> throw remoteList.exception!!
//        }
    }

    override suspend fun fetchItemFromBackend(id: Long) {
        when (val remoteItem = remote.getItem(id)) {
            is Success -> local.saveItem(remoteItem.data)
            is Error -> throw remoteItem.exception!!
        }
    }

    override suspend fun saveList(list: List<Apple>) {
        Timber.d("saveList size=${list.size}")
        coroutineScope {
            launch { local.saveList(list) }
            //TODO: uncomment after server setup
//                launch { remote?.saveList(list) }
        }
    }

    override suspend fun getList(): Result<List<Apple>> = local.getList()

    override fun observeList() = local.observeList()

    override suspend fun deleteAll() {
        coroutineScope {
            launch { local.deleteAll() }
            //TODO: uncomment after server setup
//            launch { remote?.deleteAll() }
        }
    }
}