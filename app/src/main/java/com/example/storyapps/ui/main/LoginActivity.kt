package com.example.storyapps.ui.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.storyapps.data.local.ViewModelFactory
import com.example.storyapps.data.local.preferences.LoginPreferences
import com.example.storyapps.data.model.CheckLogin
import com.example.storyapps.data.model.LoginRequest
import com.example.storyapps.data.model.LoginResult
import com.example.storyapps.databinding.ActivityLoginBinding
import com.example.storyapps.viewmodels.LoginViewModel
import com.example.storyapps.utils.Result
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var loginPreferences: LoginPreferences
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        register()
        btnLogin()
        playAnimation()
        showLoading(false)
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val email = ObjectAnimator.ofFloat(binding.tiEmail, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.tiPassword, View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val forRegister = ObjectAnimator.ofFloat(binding.forRegister, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.register, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(forRegister, register)
        }

        AnimatorSet().apply {
            playSequentially(email, password, btnLogin, together)
            start()
        }
    }

    private fun showLoading(state: Boolean) {
        if(state) {
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun btnLogin() {
        binding.btnLogin.setOnClickListener {
            val tvEmail = binding.edLoginEmail.text.toString()
            val tvPassword = binding.edLoginPassword.text.toString()
            val request = LoginRequest(tvEmail, tvPassword)
            if(checkData(tvEmail, tvPassword)) {
                post(request)
            }else {
                showText("Email atau password tidak valid")
            }
        }
    }

    private fun post(request: LoginRequest) {
        showLoading(true)
        loginViewModel.postLogin(request).observe(this) {
            when(it) {
                is Result.Success -> {
                    showLoading(false)
                    showText(it.data.message.toString())
                    if(it.data.message == "success") {
                        saveLogin(it.data.loginResult!!)
                        toMainActivity(it.data.loginResult)
                    }
                    finish()
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
                else -> {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        }
    }

    private fun saveLogin(loginResult: LoginResult) {
        loginPreferences = LoginPreferences(this)
        loginPreferences.setLogin(
            CheckLogin(
                true,
                loginResult.userId,
                loginResult.name,
                loginResult.token
            )
        )

    }

    private fun toMainActivity(result: LoginResult) {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.putExtra("result", result)
        startActivity(intent)
        finish()
    }

    private fun register() {
        binding.register.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
    }

    private fun showText(text: String) {
        Toast.makeText(this@LoginActivity, text, Toast.LENGTH_SHORT).show()
    }

    private fun checkData(edEmail: String, edPassword: String): Boolean {
        if(edEmail.contains("@") && edEmail.contains(".com")) {
            if(edPassword.length > 7) {
                return true
            }
        }
        return false
    }
}