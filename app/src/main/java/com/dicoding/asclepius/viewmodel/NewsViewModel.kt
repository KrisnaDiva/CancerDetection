package com.dicoding.asclepius.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.remote.response.NewsResponse
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    private val _listNews = MutableLiveData<List<ArticlesItem>>()
    val listNews: MutableLiveData<List<ArticlesItem>> = _listNews

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    init {
        findUser()
    }

    private fun findUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getCancerNews()
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _listNews.value =
                        response.body()?.articles?.filterNot { it.title == "[Removed]" }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                _isLoading.value = false
                _toastMessage.value = t.message
            }
        })
    }
}

