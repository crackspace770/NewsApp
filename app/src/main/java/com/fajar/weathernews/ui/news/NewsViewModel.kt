package com.fajar.weathernews.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fajar.weathernews.data.NewsRepository
import com.fajar.weathernews.data.remote.network.ApiConfig
import com.fajar.weathernews.data.remote.response.ListNewsResponse
import com.fajar.weathernews.data.remote.response.NewsResponse
import com.fajar.weathernews.data.utils.Constant.Companion.API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {

    fun getHeadlineNews() = newsRepository.getHeadlineNews()

    fun getBusinessHeadlineNews() = newsRepository.getBusinessHeadline()


}
