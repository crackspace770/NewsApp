package com.fajar.weathernews.ui.science

import androidx.lifecycle.ViewModel
import com.fajar.weathernews.data.NewsRepository

class ScienceViewModel(private val newsRepository: NewsRepository):ViewModel() {

    fun getScienceNews() = newsRepository.getScienceHeadline()

}