package com.example.newsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repo.NewsRersotry
import com.example.newsapp.utilite.ResonseState
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newReprositry: NewsRersotry):ViewModel() {
    val news: MutableLiveData<ResonseState<NewsResponse>> = MutableLiveData()
    val breakingnewspage = 1
    val searchnews: MutableLiveData<ResonseState<NewsResponse>> = MutableLiveData()
    val searchnewspage = 1
    init {
        getbreakingnews("us")
    }
    fun getbreakingnews(countrycode: String) = viewModelScope.launch {
        news.postValue(ResonseState.Load())
        val response = newReprositry.getBreakingFun(countrycode, breakingnewspage)
    }
     fun getsearchnews(searchquary:String)=viewModelScope.launch {
         searchnews.postValue(ResonseState.Load())
         val response=newReprositry.srearchNews(searchquary,searchnewspage)
     }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : ResonseState<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return ResonseState.Success(resultResponse)
            }
        }
        return ResonseState.Error(response.message())
    }
    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : ResonseState<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return ResonseState.Success(resultResponse)
            }
        }
        return ResonseState.Error(response.message())
    }
    fun savarticle(article: Article)=viewModelScope.launch {
        newReprositry.getoursavenews()
    }
    fun getSavedNews() = newReprositry.getoursavenews()

    fun deletenews(article: Article){
        viewModelScope.launch { newReprositry.deletearticle(article) }
    }




}