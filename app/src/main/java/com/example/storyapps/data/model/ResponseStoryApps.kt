package com.example.storyapps.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RegisterRequest(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null
)

data class RegisterResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class LoginRequest(
    val email: String? = null,
    val password: String? = null
)

data class LoginResponse(
    val error: Boolean? = null,
    val message: String? = null,
    val loginResult: LoginResult? = null
)


@Parcelize
data class LoginResult(
    val userId: String? = null,
    val name: String? = null,
    val token: String? = null
): Parcelable

@Parcelize
data class CheckLogin(
    var isLogin: Boolean = false,
    var userId: String? = null,
    var name: String? = null,
    var token: String? = null
): Parcelable


data class StoriesResponse(
    val error: Boolean,
    val message: String,
    val listStory: ArrayList<ListStory>
)

@Parcelize
data class ListStory(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val photoUrl: String? = null,
    val createdAt: String? = null,
    val lat: Double,
    val lon: Double
): Parcelable

data class FileUploadResponse(
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String
)
