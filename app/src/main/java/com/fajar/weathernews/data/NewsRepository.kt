package com.fajar.weathernews.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.fajar.weathernews.data.local.entity.NewsEntity
import com.fajar.weathernews.data.local.room.NewsDao
import com.fajar.weathernews.data.remote.network.ApiService
import com.fajar.weathernews.data.utils.Constant

class NewsRepository(
    private val apiService: ApiService,
    private val newsDao: NewsDao
) {

    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        emit(Result.Loading)
        try{
            val response = apiService.getTopHeadline(Constant.API_KEY)
            val articles = response.articles
            val newsList = articles.map { articles ->
                NewsEntity(
                    articles.source.name,
                    articles.title,
                    articles.publishedAt,
                    articles.urlToImage,
                    articles.url
                )
            }
            emit(Result.Success(newsList))
        }catch (e:Exception) {
            Log.d("NewsRepository","getHeadlineNews:${e.message.toString()}")
            emit(Result.Error(e.message.toString()))

        }
    }

    fun getBusinessHeadline(): LiveData<Result<List<NewsEntity>>> = liveData {

        emit(Result.Loading)
        try{
            val response = apiService.getBusinessTopHeadline(Constant.API_KEY)
            val articles = response.articles
            val newsList = articles.map { articles ->
                NewsEntity(
                    articles.source.name,
                    articles.title,
                    articles.publishedAt,
                    articles.urlToImage,
                    articles.url
                )
            }
            emit(Result.Success(newsList))
        }catch (e:Exception) {
            Log.d("NewsRepository","getHeadlineNews:${e.message.toString()}")
            emit(Result.Error(e.message.toString()))

        }

    }

    fun getBookmarkedNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }

    suspend fun saveNews(news: NewsEntity) {
        newsDao.saveNews(news)
    }

    suspend fun deleteNews(title: String) {
        newsDao.deleteNews(title)
    }

    fun isNewsBookmarked(title: String): LiveData<Boolean> {
        return newsDao.isNewsBookmarked(title)
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao)
            }.also { instance = it }
    }

}