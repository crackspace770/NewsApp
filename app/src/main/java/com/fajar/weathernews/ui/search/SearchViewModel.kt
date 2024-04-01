package com.fajar.weathernews.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.fajar.weathernews.data.NewsRepository
import com.fajar.weathernews.data.Result
import com.fajar.weathernews.data.local.entity.NewsEntity
import com.fajar.weathernews.data.utils.Constant

class SearchViewModel(private val newsRepository: NewsRepository):ViewModel() {

    private val searchQuery = MutableLiveData<String>()

    fun setSearchQuery(query: String) {
        this.searchQuery.value = query
    }

    var searchResult: LiveData<Result<List<NewsEntity>>> =
        searchQuery.switchMap { query->
            newsRepository.getSearchNews(query)
        }

}