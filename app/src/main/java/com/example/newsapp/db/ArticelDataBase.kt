package com.example.newsapp.db

import android.content.Context
import androidx.room.*
import androidx.room.RoomDatabase
import com.example.newsapp.models.Article
import com.example.newsapp.api.Converters

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticelDataBase : RoomDatabase() {

    abstract fun getArticleDao(): AtricleDao

    companion object {
        @Volatile
        private var instance: ArticelDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticelDataBase::class.java,
                "article_db.db"
            ).build()
    }
}
