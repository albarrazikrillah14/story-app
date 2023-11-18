package com.example.storyapps.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapps.data.local.StoryRepository
import com.example.storyapps.data.model.ListStory


class MainViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getStory() : LiveData<PagingData<ListStory>> {
        return storyRepository.getStory().cachedIn(viewModelScope)
    }

}