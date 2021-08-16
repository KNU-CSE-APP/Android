package com.example.knucseapp.ui.auth

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("MY_ACCOUNT", Context.MODE_PRIVATE)

    fun getUserId(): String {
        return prefs.getString("ID", "").toString()
    }
    fun setUserId(str: String) {
        prefs.edit().putString("ID", str).apply()
    }

    fun getUserPass(): String {
        return prefs.getString("PASS", "").toString()
    }
    fun setUserPass(str: String) {
        prefs.edit().putString("PASS", str).apply()
    }

    fun getUserNickname(): String {
        return prefs.getString("NICKNAME", "").toString()
    }
    fun setUserNickname(str: String) {
        prefs.edit().putString("NICKNAME", str).apply()
    }

    fun clear(){
        prefs.edit().clear().commit()
    }
}
