package com.example.storyapps.viewmodels

import androidx.lifecycle.ViewModel
import com.example.storyapps.data.local.StoryRepository
import com.example.storyapps.data.model.LoginRequest

class LoginViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postLogin(request: LoginRequest) = storyRepository.postLogin(request)
}