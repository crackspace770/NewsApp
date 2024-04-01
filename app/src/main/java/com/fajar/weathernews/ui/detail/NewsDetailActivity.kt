package com.fajar.weathernews.ui.detail

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fajar.weathernews.R
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

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_bookmark -> {
                    if (news != null) {
                        viewModel.changeBookmark(news)
                    }
                    true
                }
                else -> false
            }
        }

        viewModel.bookmarkStatus.observe(this) {status->
            setBookmarkState(status)
        }

        binding.webView.webViewClient = WebViewClient()
        if (news != null) {
            binding.webView.loadUrl(news.url.toString())
        }

        if (news != null) {
            viewModel.setNewsData(news)
        }

    }

    private fun setBookmarkState(state:Boolean) {
        val menuItem = binding.topAppBar.menu.findItem(R.id.action_bookmark)
        if (state){
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_selected)
        } else{
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_unselected)
        }
    }


    companion object{
        const val EXTRA_NEWS = "extra_news"
    }

}