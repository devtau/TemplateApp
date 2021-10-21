package com.devtau.template.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.devtau.template.R
import com.devtau.template.data.Result
import com.devtau.template.data.Result.Success
import com.devtau.template.data.Result.Error
import com.devtau.template.data.model.Apple
import kotlinx.coroutines.delay

/**
 * Test double for [Apple]s DataSource
 * @param list fake list of [Apple]s
 */
class FakeApplesDataSource(
    private val list: MutableList<Apple>?
): BaseDataSource<Apple> {

    override suspend fun saveItem(item: Apple): Result<Long> {
        list ?: return Error(R.string.list_is_null)
        list.add(item)
        return Success(list.indexOf(item).toLong())
    }

    override suspend fun getItem(id: Long): Result<Apple> {
        list ?: return Error(R.string.list_is_null)
        val item = list.firstOrNull { it.id == id }
        item ?: return Error(R.string.object_not_found)
        return Success(item)
    }

    override fun observeItem(id: Long): LiveData<Result<Apple?>> = liveData {
        while (true) {
            emit(Success(Apple.getMock()))
            delay(EMIT_DELAY_MS)
        }
    }

    override suspend fun deleteItem(item: Apple): Result<Boolean> {
        list ?: return Error(R.string.list_is_null)
        val result = list.remove(item)
        return if (result) Success(result) else Error(R.string.object_not_found)
    }

    override suspend fun saveList(list: List<Apple>) {
        this.list?.clear()
        this.list?.addAll(list)
    }

    override suspend fun getList(): Result<List<Apple>> {
        return if (list == null) {
            Error(R.string.list_is_null)
        } else {
            Success(ArrayList(list))
        }
    }

    override fun observeList(): LiveData<Result<List<Apple>>> = liveData {
        while (true) {
            emit(if (list == null) Error(R.string.list_is_null) else Success(list))
            delay(EMIT_DELAY_MS)
        }
    }

    override suspend fun deleteAll(): Result<Int> {
        list ?: return Error(R.string.list_is_null)
        val size = list.size
        list.clear()
        return Success(size)
    }

    companion object {
        private const val EMIT_DELAY_MS = 1000L
    }
}