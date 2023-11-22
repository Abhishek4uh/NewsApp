package com.example.newsapp.utils

import java.text.SimpleDateFormat
import java.util.*

object AppConstant {

    //Base URl
    const val BASE_URL:String ="https://candidate-test-data-moengage.s3.amazonaws.com"


    fun dateAndTimeFormatter(input: String): String {
        //Helper function which convert this normal time format
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd:MM:yyyy 'T' HH:mm:ss", Locale.getDefault())
        val date = inputFormat.parse(input)
        return outputFormat.format(date!!)
    }
}