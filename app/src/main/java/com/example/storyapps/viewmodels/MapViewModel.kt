package com.example.storyapps.viewmodels

import androidx.lifecycle.ViewModel
import com.example.storyapps.data.local.StoryRepository

class MapViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getStoryLocation(token: String) = storyRepository.getStoryLocation(token)
}