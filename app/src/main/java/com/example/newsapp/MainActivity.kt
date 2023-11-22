package com.example.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.adapter.NewsListAdapter
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.model.Article
import com.example.newsapp.network.RetrofitHelper
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.viewmodel.NewsViewmodelFactory
import com.example.newsapp.services.ApiServices
import com.example.newsapp.utils.NetworkResult
import com.example.newsapp.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private lateinit var newsListAdapter: NewsListAdapter
    private lateinit var viewModel:NewsViewModel

    companion object {
        private const val TAG = "APPDATA_MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)
        Log.d(TAG,"onCreate")

        //Start..
        initializingViewModel()
        newsListAdapter= NewsListAdapter{item -> itemClicked(item)}
        mBinding?.recyclerView?.adapter=newsListAdapter
        initObserver()
        initClick()
    }

    private fun initObserver() {
        viewModel.newsResponse.observe(this) {
            when(it) {
                is NetworkResult.Loading -> {
                    Log.d(TAG,"Loading..")
                    mBinding?.progressBar?.isVisible=it.isLoading
                }
                is NetworkResult.Failure -> {
                    Log.d(TAG,"Error :(")
                    mBinding?.progressBar?.isVisible = false
                    mBinding?.clNoInternet?.visibility=View.VISIBLE
                }

                is  NetworkResult.Success -> {
                    Log.d(TAG,"Success :) ")
                    newsListAdapter.populateData(it.data!!)
                    mBinding?.progressBar?.isVisible = false
                    mBinding?.clNoInternet?.visibility=View.GONE
                    mBinding?.recyclerView?.visibility=View.VISIBLE
                }
            }
        }
    }

    private fun initializingViewModel() {
        val newsServices= RetrofitHelper.getInstance().create(ApiServices::class.java)
        val repository= NewsRepository(newsServices)
        viewModel = ViewModelProvider(this, NewsViewmodelFactory(repository))[NewsViewModel::class.java]
    }

    private fun itemClicked(partItem : Article) {
        val rootView: View = findViewById(android.R.id.content)
        Snackbar.make(rootView, "Opening the URl....", Snackbar.LENGTH_SHORT).show()
        val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(partItem.url))
        startActivity(urlIntent)
    }

    private fun initClick() {
        mBinding?.btnRetry?.setOnClickListener {
            viewModel.fetchAllMovies()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.oldToNew ->{
                newsListAdapter.sortOldToNew()
            }
            R.id.newToOLd->{
                newsListAdapter.sortNewToOld()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}