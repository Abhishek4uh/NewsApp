package com.example.newsapp.services


import com.example.newsapp.model.NewsArticlesList
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    @GET("/Android/news-api-feed/staticResponse.json")
    suspend fun getNewsData(): Response<NewsArticlesList>
}