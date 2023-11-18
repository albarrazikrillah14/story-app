package com.example.storyapps.di

import android.content.Context
import android.util.Log
import com.example.storyapps.data.local.StoryRepository
import com.example.storyapps.data.local.preferences.LoginPreferences
import com.example.storyapps.data.remote.ApiClient


object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val preferences = LoginPreferences(context)
        val apiService = ApiClient.storyAppService
        return StoryRepository(preferences, apiService)
    }

}