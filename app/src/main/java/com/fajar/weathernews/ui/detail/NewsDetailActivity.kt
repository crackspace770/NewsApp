package com.fajar.weathernews.ui.detail

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fajar.weathernews.data.local.entity.NewsEntity
import com.fajar.weathernews.data.utils.ViewModelFactory
import com.fajar.weathernews.databinding.ActivityNewsDetailBinding

class NewsDetailActivity:AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: NewsDetailViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val news = intent.getParcelableExtra<NewsEntity>(EXTRA_NEWS)

        if (news != null) {
            binding.topAppBar.title = news.title
        }

        binding.webView.webViewClient = WebViewClient()
        if (news != null) {
            binding.webView.loadUrl(news.url.toString())
        }

    }


    companion object{
        const val EXTRA_NEWS = "extra_news"
    }

}