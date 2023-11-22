package com.example.newsapp.repository


import android.util.Log
import com.example.newsapp.services.ApiServices
import com.example.newsapp.utils.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class NewsRepository(private val apiService: ApiServices){

    companion object {
        private const val TAG = "APPDATA_Repository"
    }

    suspend fun getNewsData()  = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.getNewsData()
        Log.d(TAG,response.body()?.articles.toString())
        emit(NetworkResult.Success(response.body()?.articles))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
        Log.d(TAG,e.message.toString())
    }
}