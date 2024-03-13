package com.fajar.weathernews.data.remote.response


data class ListNewsResponse (
    val status: String,
    val totalResults: Long,
    val articles: List<NewsResponse>
)


data class NewsResponse (
    val source: Source,
    val author: String? = null,
    val title: String,
    val description: String? = null,
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: String,
    val content: String? = null
)


data class Source (
    val id: String? = null,
    val name: String
)