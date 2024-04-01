package com.fajar.weathernews.ui.tech

import androidx.lifecycle.ViewModel
import com.fajar.weathernews.data.NewsRepository

class TechViewModel(private val newsRepository: NewsRepository): ViewModel() {

    fun getTechHeadline() = newsRepository.getTechHeadline()

}