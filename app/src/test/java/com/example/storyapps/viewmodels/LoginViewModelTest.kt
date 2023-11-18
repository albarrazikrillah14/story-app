package com.example.storyapps.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapps.data.local.StoryRepository
import com.example.storyapps.data.model.LoginRequest
import com.example.storyapps.data.model.LoginResponse
import com.example.storyapps.utils.DataDummy
import com.example.storyapps.utils.Result
import com.example.storyapps.utils.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var loginViewModel: LoginViewModel
    private val dummyLogin = DataDummy.generateDummyLoginEntity()

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(storyRepository)
    }

    @Test
    fun `when Login Should Not Null and Return Success`() {

        val expectedLogin = MutableLiveData<Result<LoginResponse>>()
        expectedLogin.value = Result.Success(dummyLogin)
        `when`(storyRepository.postLogin(inputSuccess)).thenReturn(expectedLogin)

        val actualLogin = loginViewModel.postLogin(inputSuccess).getOrAwaitValue()

        assertNotNull(actualLogin)
        assertTrue(actualLogin is Result.Success)
        Mockito.verify(storyRepository).postLogin(inputSuccess)
    }

    @Test
    fun `when Login Null and Return Error Value`() {
        val expectedLogin = MutableLiveData<Result<LoginResponse>>()
        expectedLogin.value = Result.Error("error")
        `when`(storyRepository.postLogin(inputError)).thenReturn(expectedLogin)

        val actualLogin = loginViewModel.postLogin(inputError).getOrAwaitValue()

        assertNotNull(actualLogin)
        assertTrue(actualLogin is Result.Error)
        Mockito.verify(storyRepository).postLogin(inputError)
    }

    companion object {
        val inputSuccess = LoginRequest(
            "email",
            "password"
        )

        val inputError = LoginRequest(
            null,
            null
        )
    }
}