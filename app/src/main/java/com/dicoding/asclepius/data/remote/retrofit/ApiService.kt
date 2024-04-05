package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/v2/top-headlines?q=cancer&category=health&language=en&apiKey=YOUR_API_KEY")
    fun getCancerNews(): Call<NewsResponse>
}