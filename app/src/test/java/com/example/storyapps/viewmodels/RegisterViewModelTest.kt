package com.example.storyapps.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapps.data.local.StoryRepository
import com.example.storyapps.data.model.RegisterRequest
import com.example.storyapps.data.model.RegisterResponse
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
class RegisterViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var registerViewModel: RegisterViewModel
    private val dummyRegister = DataDummy.generateDummyRegisterEntity()

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(storyRepository)
    }

    @Test
    fun `when Register Should Not Null and Return Success Value`() {
        val expectedRegister = MutableLiveData<Result<RegisterResponse>>()
        expectedRegister.value = Result.Success(dummyRegister)

        `when`(storyRepository.postRegister(inputSuccess)).thenReturn(expectedRegister)

        val actualRegister = registerViewModel.postRegister(inputSuccess).getOrAwaitValue()

        assertNotNull(actualRegister)
        assertTrue(actualRegister is Result.Success)
        Mockito.verify(storyRepository).postRegister(inputSuccess)
    }

    @Test
    fun `when Register Null and Return Error Value`() {
        val expectedRegister = MutableLiveData<Result<RegisterResponse>>()
        expectedRegister.value = Result.Error("Error")

        `when`(storyRepository.postRegister(inputError)).thenReturn(expectedRegister)

        val actualRegister = registerViewModel.postRegister(inputError).getOrAwaitValue()

        assertNotNull(actualRegister)
        assertTrue(actualRegister is Result.Error)
        Mockito.verify(storyRepository).postRegister(inputError)
    }
    companion object {
        val inputSuccess = RegisterRequest(
            "name",
            "email",
            "password"
        )

        val inputError = RegisterRequest (
            null,
            null,
            null
        )
    }
}