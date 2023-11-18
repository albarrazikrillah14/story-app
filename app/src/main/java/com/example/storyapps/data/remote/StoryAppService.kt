package com.example.storyapps.data.remote

import com.example.storyapps.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface StoryAppService {
    @POST("register")
    suspend fun postRegister(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun postLogin(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("stories")
    suspend fun allStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,)
            : StoriesResponse


    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Double?,
        @Part("lon") lon: Double?
    ): FileUploadResponse

    @GET("stories")
    suspend fun getStoriesLocation(
        @Header("Authorization") token: String,
        @Query("location") location : Int = 1,
    ): StoriesResponse
}