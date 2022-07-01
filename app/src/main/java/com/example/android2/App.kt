package com.example.android2

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import com.example.android2.room.AppDataBase

class App: Application() {
    companion object{
        lateinit var database: AppDataBase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this,AppDataBase::class.java,"database")
            .allowMainThreadQueries()
            .build()
    }
}