package com.example.android2.room

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.android2.models.News

@Database(entities = [News::class],version = 1)

abstract class AppDataBase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

}