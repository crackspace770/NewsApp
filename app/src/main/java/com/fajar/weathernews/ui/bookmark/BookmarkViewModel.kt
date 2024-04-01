package com.fajar.weathernews.ui.bookmark

import androidx.lifecycle.ViewModel
import com.fajar.weathernews.data.NewsRepository

class BookmarkViewModel(private val newsRepository: NewsRepository):ViewModel() {

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()

}