package com.fajar.weathernews.data.di

import android.content.Context
import com.fajar.weathernews.data.NewsRepository
import com.fajar.weathernews.data.local.room.NewsDatabase
import com.fajar.weathernews.data.remote.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.provideApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        return NewsRepository.getInstance(apiService, dao)
    }
}