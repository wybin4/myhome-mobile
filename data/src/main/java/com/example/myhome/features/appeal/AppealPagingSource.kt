package com.example.myhome.features.appeal

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.dto.EventListRequest
import com.example.myhome.features.event.dto.MetaRequest
import com.example.myhome.features.event.models.EventType
import androidx.paging.PagingState
import com.example.myhome.ConstantsData.Companion.PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class AppealPagingSource(private val eventApiService: EventApiService) : PagingSource<Int, AppealListItemResponse>() {
    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AppealListItemResponse> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val events = eventApiService.listEvent(
                EventListRequest(
                    userId = 1,
                    events = listOf(EventType.Appeal),
                    meta = MetaRequest(page, PAGE_SIZE)
                )
            )
            LoadResult.Page(
                data = events.appeals,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (events.appeals.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AppealListItemResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}