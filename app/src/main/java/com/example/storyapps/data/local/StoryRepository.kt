package com.example.storyapps.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapps.data.local.preferences.LoginPreferences
import com.example.storyapps.data.model.FileUploadResponse
import com.example.storyapps.data.model.ListStory
import com.example.storyapps.data.model.LoginRequest
import com.example.storyapps.data.model.LoginResponse
import com.example.storyapps.data.model.RegisterRequest
import com.example.storyapps.data.model.RegisterResponse
import com.example.storyapps.data.model.StoriesResponse
import com.example.storyapps.data.remote.ApiClient
import com.example.storyapps.data.remote.StoryAppService
import com.example.storyapps.utils.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody


class StoryRepository(private val preferences: LoginPreferences, private val apiService: StoryAppService) {

    fun addStory(token: String, file: MultipartBody.Part, description: RequestBody, lat: Double? = null, lon: Double? = null): LiveData<Result<FileUploadResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService
                .uploadImage(token, file, description,lat, lon)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postLogin(request: LoginRequest): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService
                .postLogin(request)
            emit(Result.Success(response))
        } catch(e: Exception) {
            Log.d("Login", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postRegister(request: RegisterRequest): LiveData<Result<RegisterResponse>> {
        return liveData {
            emit(Result.Loading)
            try {
                val response = ApiClient
                    .storyAppService
                    .postRegister(request)
                emit(Result.Success(response))
            }catch(e: Exception)  {
                Log.d("Register", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }

        }
    }

    fun getStoryLocation(token: String): LiveData<Result<StoriesResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStoriesLocation(token, 1)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getStory(): LiveData<PagingData<ListStory>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, preferences)
            }
        ).liveData
    }
}