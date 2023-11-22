package com.example.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.NewsItemBinding
import com.example.newsapp.model.Article
import com.example.newsapp.utils.AppConstant

class NewsListAdapter(val clickListener: (Article)-> Unit):RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>(){

    private var context: Context? = null
    private var dataList: MutableList<Article> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        context=parent.context
        val view = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentData= dataList[position]

        //setting the views...
        holder.mBinding.tvTitle.text=currentData.title
        holder.mBinding.tvDescription.text =currentData.description
        holder.mBinding.tvPublishedDate.text=AppConstant.dateAndTimeFormatter(currentData.publishedAt)

        //setting the image..
        if(currentData.publishedAt.isNotEmpty()){
            Glide.with(context!!).load(currentData.urlToImage).into(holder.mBinding.ivNews)
        }
        holder.itemView.setOnClickListener {
            clickListener(currentData)
        }

    }

    fun populateData(results: List<Article>) {
        dataList.clear()
        dataList.addAll(results)
        notifyDataSetChanged()
    }



    fun sortOldToNew(){
        dataList= dataList.sortedBy { it.publishedAt }.toMutableList()
        notifyDataSetChanged()
    }

    fun sortNewToOld(){
        dataList = dataList.sortedByDescending { it.publishedAt }.toMutableList()
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(val mBinding: NewsItemBinding) : RecyclerView.ViewHolder(mBinding.root)

}