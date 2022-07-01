package com.example.android2.room

import androidx.room.*

import com.example.android2.models.News


@Dao
interface NewsDao {

    @Query("SELECT * FROM News")
    fun getAll(): List<News>?


    @Insert
    fun insert(news: News)

    @Update
    fun update(news: News)

    @Delete
    fun delete(news: News)

}