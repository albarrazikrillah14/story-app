package com.example.storyapps.viewmodels

import androidx.lifecycle.ViewModel
import com.example.storyapps.data.local.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: Double? = null,
        lon: Double? = null
    ) = storyRepository.addStory(
        token,
        file,
        description,
        lat,
        lon
    )
}