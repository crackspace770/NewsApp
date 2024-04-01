package com.fajar.weathernews.data.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fajar.weathernews.data.NewsRepository
import com.fajar.weathernews.data.di.Injection
import com.fajar.weathernews.ui.bookmark.BookmarkViewModel
import com.fajar.weathernews.ui.detail.NewsDetailViewModel
import com.fajar.weathernews.ui.news.NewsViewModel
import com.fajar.weathernews.ui.science.ScienceViewModel
import com.fajar.weathernews.ui.search.SearchViewModel
import com.fajar.weathernews.ui.tech.TechViewModel

class ViewModelFactory private constructor(private val newsRepository: NewsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsRepository) as T
        } else if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
            return NewsDetailViewModel(newsRepository) as T
        }
        else if (modelClass.isAssignableFrom(TechViewModel::class.java)) {
            return TechViewModel(newsRepository) as T
        }
        else if (modelClass.isAssignableFrom(ScienceViewModel::class.java)) {
            return ScienceViewModel(newsRepository) as T
        }
        else if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(newsRepository) as T
        }
        else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}