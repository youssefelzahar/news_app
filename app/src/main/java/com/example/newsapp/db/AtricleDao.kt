package com.example.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.models.Article

@Dao
interface AtricleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article):Long
    @Query("Select * From articles")
    fun getallaticles(): LiveData<List<Article>>
    @Delete
    suspend fun deletearticles(article: Article)
}