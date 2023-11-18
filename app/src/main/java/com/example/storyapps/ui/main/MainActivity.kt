package com.example.storyapps.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapps.R
import com.example.storyapps.adapter.LoadingStoryAdapter
import com.example.storyapps.adapter.StoryAdapter
import com.example.storyapps.data.local.ViewModelFactory
import com.example.storyapps.data.local.preferences.LoginPreferences
import com.example.storyapps.databinding.ActivityMainBinding
import com.example.storyapps.ui.map.MapsActivity
import com.example.storyapps.viewmodels.MainViewModel



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mLoginPreferences: LoginPreferences
    private lateinit var mainViewModel: MainViewModel
    private lateinit var factory: ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mLoginPreferences = LoginPreferences(this)

        factory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        binding.rvStory.layoutManager = LinearLayoutManager(this)

        isLogin()

    }
    private fun isLogin() {
       val getLogin = mLoginPreferences.getLogin()
       if(getLogin.isLogin) {
           getData()
           addStory()
       }else {
           startActivity(Intent(this@MainActivity, LoginActivity::class.java))
           finish()
       }
    }

    private fun getData() {
        val adapter = StoryAdapter()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStoryAdapter {
                adapter.retry()
            }
        )

        mainViewModel.getStory().observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun addStory() {
        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddStoryActivity::class.java))
            finish()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu1 -> {
                mLoginPreferences.clearSession()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
            R.id.menu2 -> showMap()
        }
        return true
    }

    private fun showMap() {
        val intent = Intent(this@MainActivity, MapsActivity::class.java)
        startActivity(intent)
    }
}