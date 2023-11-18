package com.example.storyapps.viewmodels


import androidx.lifecycle.*
import com.example.storyapps.data.local.StoryRepository
import com.example.storyapps.data.model.RegisterRequest


class RegisterViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postRegister(request: RegisterRequest) = storyRepository.postRegister(request)
}