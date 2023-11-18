package com.example.storyapps.viewmodels


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapps.data.local.StoryRepository
import com.example.storyapps.data.model.FileUploadResponse
import com.example.storyapps.utils.DataDummy
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.File
import com.example.storyapps.utils.Result
import com.example.storyapps.utils.getOrAwaitValue
import com.example.storyapps.viewmodels.MapViewModelTest.Companion.token
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var addStoryViewModel: AddStoryViewModel
    private val dummyAddStory = DataDummy.generateDummyAddStoryEntity()

    @Before
    fun startUp() {
        addStoryViewModel = AddStoryViewModel(storyRepository)
    }

    @Test
    fun `when Upload Should Not Null and Return Success`() {
        val desc = "description".toRequestBody("text/plain".toMediaType())
        val file = mock(File::class.java)
        val requestImage = file.asRequestBody("image/*".toMediaTypeOrNull())
        val fileImage: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            "name_file",
            requestImage
        )

        val expectedAddStory = MutableLiveData<Result<FileUploadResponse>>()
        expectedAddStory.value = Result.Success(dummyAddStory)
        `when`(storyRepository.addStory(TOKEN, fileImage, desc, lat, lon )).thenReturn(expectedAddStory)

        val actualStory = addStoryViewModel.addStory(TOKEN, fileImage, desc, lat, lon).getOrAwaitValue()

        Mockito.verify(storyRepository).addStory(TOKEN, fileImage, desc, lat, lon)
        assertNotNull(actualStory)
        assertTrue(actualStory is Result.Success)

    }

    @Test
    fun `when Upload have No Network and Return Error`() {
        val desc = "description".toRequestBody("text/plain".toMediaType())
        val file = mock(File::class.java)
        val requestImage = file.asRequestBody("image/*".toMediaTypeOrNull())
        val fileImage: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            "name_file",
            requestImage
        )

        val expectedAddStory = MutableLiveData<Result<FileUploadResponse>>()
        expectedAddStory.value = Result.Error("Error")
        `when`(storyRepository.addStory(TOKEN, fileImage, desc, lat, lon )).thenReturn(expectedAddStory)

        val actualStory = addStoryViewModel.addStory(TOKEN, fileImage, desc, lat, lon).getOrAwaitValue()

        Mockito.verify(storyRepository).addStory(TOKEN, fileImage, desc, lat, lon)
        assertNotNull(actualStory)
        assertTrue(actualStory is Result.Error)
    }

    companion object {
        private const val TOKEN = "Bearer Token"
        private const val lat = 1.4
        private const val lon = 0.5
    }
}