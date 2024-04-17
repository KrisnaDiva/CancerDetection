package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/v2/top-headlines?q=cancer&category=health&language=en&apiKey=37b70e0d9d2c4e71b9afe60b080d7071")
    fun getCancerNews(): Call<NewsResponse>
}