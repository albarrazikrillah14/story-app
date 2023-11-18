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
import com.example.storyapps.data.model.RegisterRequest
import com.example.storyapps.databinding.ActivityRegisterBinding
import com.example.storyapps.utils.Result
import com.example.storyapps.viewmodels.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var factory: ViewModelFactory
    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        registerViewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        login()
        btnLogin()
        showLoading(false)
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivRegister, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val name = ObjectAnimator.ofFloat(binding.tiName, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.tiEmail, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.tiPassword, View.ALPHA, 1f).setDuration(500)
        val forLogin = ObjectAnimator.ofFloat(binding.forLogin, View.ALPHA, 1f).setDuration(500)
        val button = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.login, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(forLogin, login)
        }

        AnimatorSet().apply {
            playSequentially(name, email, password, button, together)
            start()
        }
    }


    private fun btnLogin() {
       binding.btnRegister.setOnClickListener {
           val edName = binding.edRegisterName.text.toString()
           val edEmail = binding.edRegisterEmail.text.toString()
           val edPass = binding.edRegisterPassword.text.toString()

           if(checkData(edName, edEmail, edPass)) {
               val user = RegisterRequest(edName, edEmail, edPass)
               post(user)
           }else {
               showText("Isi data dengan benar")
           }

       }
    }

    private fun post(user: RegisterRequest) {
        registerViewModel.postRegister(user).observe(this) {
            when(it) {
                is Result.Success -> {
                    showLoading(false)
                    showText(it.data.message)
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showText(it.error)
                    showLoading(false)
                }
            }

        }
    }
    private fun checkData(edName: String, edEmail: String, edPassword: String): Boolean {
        if(edName.isNotEmpty()) {
            if(edEmail.contains("@") && edEmail.contains(".com")) {
                if(edPassword.length > 7) {
                    return true
                }
            }
        }
        return false
    }

    private fun login() {
        binding.login.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }
    private fun showText(text: String) {
        Toast.makeText(this@RegisterActivity, text, Toast.LENGTH_SHORT).show()
    }
    private fun showLoading(state: Boolean) {
        if(state) {
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

}