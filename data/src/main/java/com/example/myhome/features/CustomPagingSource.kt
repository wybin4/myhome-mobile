package com.example.myhome.features

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

abstract class CustomPagingSource<T : Any> : PagingSource<Int, T>() {
    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    abstract suspend fun invoke(page: Int): List<T>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val request = invoke(page)
            LoadResult.Page(
                data = request,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (request.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
