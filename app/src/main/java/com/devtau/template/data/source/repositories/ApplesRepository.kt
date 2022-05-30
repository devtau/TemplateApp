package com.devtau.template.data.source.repositories

import com.devtau.template.data.Result
import com.devtau.template.data.Result.Error
import com.devtau.template.data.Result.Success
import com.devtau.template.data.Result.Loading
import com.devtau.template.data.model.Apple
import com.devtau.template.data.source.local.apples.ApplesLocalDataSource
import com.devtau.template.data.source.remote.apples.ApplesRemoteDataSource
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
            is Loading -> { /*NOP*/ }
        }
        return localId
    }

    override suspend fun getItem(id: Long): Result<Apple> = local.getItem(id)

    override fun observeItem(id: Long) = local.observeItem(id)

    override suspend fun deleteItem(item: Apple) {
        local.deleteItem(item)
        //TODO: uncomment after server setup
//        remote.deleteItem(item)
    }

    override suspend fun fetchItemsFromBackend() {
        //TODO: replace after server setup
        if ((local.getList() as? Success)?.data?.isEmpty() == true) {
            local.saveList(Apple.getMockLocalList())
        }

        //TODO: uncomment after server setup
//        when (val remoteList = remote.getList()) {
//            is Success -> {
//                local.deleteAll()
//                local.saveList(remoteList.data)
//            }
//            is Error -> throw remoteList.exception!!
//            is Loading -> { /*NOP*/ }
//        }
    }

    override suspend fun fetchItemFromBackend(id: Long) {
        when (val remoteItem = remote.getItem(id)) {
            is Success -> local.saveItem(remoteItem.data)
            is Error -> throw remoteItem.exception!!
            is Loading -> { /*NOP*/ }
        }
    }

    override suspend fun saveList(list: List<Apple>) {
        Timber.d("saveList size=${list.size}")
        local.saveList(list)
        //TODO: uncomment after server setup
//        remote.saveList(list)
    }

    override suspend fun getList(): Result<List<Apple>> = local.getList()

    override fun observeList() = local.observeList()

    override suspend fun deleteAll() {
        local.deleteAll()
        //TODO: uncomment after server setup
//        remote.deleteAll()
    }
}