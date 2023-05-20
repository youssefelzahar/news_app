package com.example.newsapp.repo

import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.db.ArticelDataBase
import com.example.newsapp.models.Article

class NewsRersotry (val db:ArticelDataBase){
    suspend fun getBreakingFun(countrycode:String,pagenumber:Int)=RetrofitInstance.api.getBreakingFun(countrycode,pagenumber)
    suspend fun srearchNews(searchquary: String,pagenumber: Int)=RetrofitInstance.api.searchForNews(searchquary,pagenumber)
    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)
    fun getoursavenews()=db.getArticleDao().getallaticles()
    suspend fun deletearticle(article: Article)=db.getArticleDao().deletearticles(article)

}