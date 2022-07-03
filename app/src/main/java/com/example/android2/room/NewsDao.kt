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

    @Query("SELECT * FROM news ORDER BY createdAt DESC")
    fun sortAll(): List<News>

    @Query("SELECT * FROM news WHERE title LIKE '%' || :search || '%'")
    fun getSearch(search: String?): List<News>

    @Query("SELECT * FROM news ORDER BY title ASC")
    fun sort(): List<News>

}