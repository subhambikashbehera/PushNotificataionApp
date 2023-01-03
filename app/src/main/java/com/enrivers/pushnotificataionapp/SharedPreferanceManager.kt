package com.enrivers.pushnotificataionapp

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPreferanceManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("myDb", MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun fetchValue(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun clearValue() {
        sharedPreferences.edit().clear().apply()
    }

}