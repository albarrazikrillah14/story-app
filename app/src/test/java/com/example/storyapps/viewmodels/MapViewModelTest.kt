package com.example.storyapps.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapps.data.local.StoryRepository
import com.example.storyapps.data.model.StoriesResponse
import com.example.storyapps.utils.DataDummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.example.storyapps.utils.Result
import com.example.storyapps.utils.getOrAwaitValue
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class MapViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapViewModel: MapViewModel
    private val dummyMap = DataDummy.generateDummyMapEntity()

    @Before
    fun setUp() {
        mapViewModel = MapViewModel(storyRepository)
    }

    @Test
    fun `when Get Story With Maps Should Not Null and Return Success`() {
        val expectedMap = MutableLiveData<Result<StoriesResponse>>()
        expectedMap.value = Result.Success(dummyMap)
        `when`(storyRepository.getStoryLocation(token)).thenReturn(expectedMap)

        val actualMap = mapViewModel.getStoryLocation(token).getOrAwaitValue()

        Mockito.verify(storyRepository).getStoryLocation(token)
        assertNotNull(actualMap)
        assertTrue(actualMap is Result.Success)
    }

    @Test
    fun `when Get Story With no Network and Return Error`() {
        val expectedMap = MutableLiveData<Result<StoriesResponse>>()
        expectedMap.value = Result.Error("Error")
        `when`(storyRepository.getStoryLocation(token)).thenReturn(expectedMap)

        val actualMap = mapViewModel.getStoryLocation(token).getOrAwaitValue()

        Mockito.verify(storyRepository).getStoryLocation(token)
        assertNotNull(actualMap)
        assertTrue(actualMap is Result.Error)
    }


    companion object {
        const val token = "Bearer Token"
    }
}