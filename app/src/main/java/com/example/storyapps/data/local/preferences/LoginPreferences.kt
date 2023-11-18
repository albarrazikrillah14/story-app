package com.example.storyapps.data.local.preferences

import android.content.Context
import com.example.storyapps.data.model.CheckLogin

class LoginPreferences(context: Context) {
    companion object {
        private const val isLogin = "islogin"
        private const val userId = "id"
        private const val name = "name"
        private const val token = "token"
    }

    private val preferences = context.getSharedPreferences(isLogin, Context.MODE_PRIVATE)

    fun setLogin(data: CheckLogin) {
        val editor = preferences.edit()
        editor.putBoolean(isLogin, data.isLogin)
        editor.putString(userId, data.userId)
        editor.putString(name, data.name)
        editor.putString(token, data.token)

        editor.apply()
    }

    fun getLogin(): CheckLogin {
        val model = CheckLogin()

        model.isLogin = preferences.getBoolean(isLogin, false)
        model.userId = preferences.getString(userId, "")
        model.name = preferences.getString(name, "")
        model.token = preferences.getString(token, "")

        return model
    }

    fun clearSession() {
       val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}