package com.example.android2

import android.content.Context

class Prefs(context: Context) {
    private val  preferences= context.getSharedPreferences("settings",Context.MODE_PRIVATE)
    fun saveState(){
        preferences.edit().putBoolean("isShown",true).apply()
    }
    fun isShown():Boolean{
        return preferences.getBoolean("isShown",false)
    }

    fun saveEditText(name: String) {
        preferences.edit().putString("text", name).apply()
    }
    fun isEditText(): String? {
        return preferences.getString("text", "")
    }




}