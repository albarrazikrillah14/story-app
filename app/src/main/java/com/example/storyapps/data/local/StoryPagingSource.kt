package com.example.storyapps.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storyapps.data.local.preferences.LoginPreferences
import com.example.storyapps.data.model.ListStory
import com.example.storyapps.data.remote.StoryAppService

class StoryPagingSource(
    private val apiService: StoryAppService,
    private val preferences: LoginPreferences
): PagingSource<Int, ListStory>(){
    override fun getRefreshKey(state: PagingState<Int, ListStory>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1)?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStory> {
        return try{
            val position = params.key ?: INITIAL_INDEX
            val token = "Bearer ${preferences.getLogin().token}"
            val responseData = apiService.allStories(token, position, params.loadSize)
            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if(position == INITIAL_INDEX) null else position - 1,
                nextKey = if(responseData.listStory.isEmpty()) null else position +1
            )
        }catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    companion object { const val INITIAL_INDEX = 1
    }
}