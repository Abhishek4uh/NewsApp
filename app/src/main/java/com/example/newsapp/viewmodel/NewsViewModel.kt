package com.example.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Article
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.utils.NetworkResult
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository):ViewModel() {

    companion object {
        private const val TAG = "APPDATA_VIEWMODEL"
    }

    private var _newsResponse = MutableLiveData<NetworkResult<List<Article>?>>()
    val newsResponse: LiveData<NetworkResult<List<Article>?>> = _newsResponse


    init {
        fetchAllMovies()
    }

    fun fetchAllMovies() {
        viewModelScope.launch {
            repository.getNewsData().collect {
                _newsResponse.postValue(it)
                Log.d(TAG,it.toString())
            }
        }
    }

}