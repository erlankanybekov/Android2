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

    fun saveImageView(image: String?) {
        preferences.edit().putString("image", image).apply()
    }

    fun isImageView(): String? {
        return preferences.getString("image", "")
    }

    fun saveImageView1(image: String?) {
        preferences.edit().putString("image1", image).apply()
    }

    fun isImageView1(): String? {
        return preferences.getString("image1", "")
    }

    fun saveImageView2(image: String?) {
        preferences.edit().putString("image2", image).apply()
    }

    fun isImageView2(): String? {
        return preferences.getString("image2", "")
    }




}