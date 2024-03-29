package com.fajar.weathernews.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajar.weathernews.R
import com.fajar.weathernews.data.Result
import com.fajar.weathernews.data.adapter.NewsAdapter
import com.fajar.weathernews.data.adapter.UsNewsAdapter
import com.fajar.weathernews.data.utils.HorizontalSpaceItemDecoration
import com.fajar.weathernews.data.utils.ViewModelFactory
import com.fajar.weathernews.databinding.ActivityNewsBinding
import com.fajar.weathernews.ui.detail.NewsDetailActivity

class NewsActivity:AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private val newsAdapter by lazy {NewsAdapter()}
    private val usNewsAdapter by lazy {UsNewsAdapter()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: NewsViewModel by viewModels {
            factory
        }

        newsAdapter.onItemClick = { news->
            val intent = Intent(this, NewsDetailActivity::class.java)
            intent.putExtra(NewsDetailActivity.EXTRA_NEWS, news) // Pass the surah number
            startActivity(intent)
        }

        usNewsAdapter.onItemClick = {news->
            val intent = Intent(this, NewsDetailActivity::class.java)
            intent.putExtra(NewsDetailActivity.EXTRA_NEWS, news) // Pass the surah number
            startActivity(intent)
        }

        viewModel.getHeadlineNews().observe(this@NewsActivity) { result ->

            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val newsData = result.data
                        newsAdapter.submitList(newsData)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = getString(R.string.something_wrong)
                    }

                }
            }

        }

        viewModel.getBusinessHeadlineNews().observe(this@NewsActivity) { result->

            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val newsData = result.data
                        usNewsAdapter.submitList(newsData)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = getString(R.string.something_wrong)
                    }

                }
            }

        }


        val horizontalSpacingInPixels = resources.getDimensionPixelSize(R.dimen.vertical_spacing) // Define your desired spacing dimension
        val itemDecoration = HorizontalSpaceItemDecoration(horizontalSpacingInPixels)
        binding.rvBreakingNews.addItemDecoration(itemDecoration)

        rvBreaking()
        rvNews()

    }

    private fun rvBreaking () {
        binding.rvBreakingNews.apply {
            adapter = usNewsAdapter
            layoutManager = LinearLayoutManager(this@NewsActivity, LinearLayoutManager.HORIZONTAL, false)

        }
    }

    private fun rvNews() {
        binding.rvNews.apply {
            adapter = newsAdapter // Change this line
            layoutManager = LinearLayoutManager(this@NewsActivity, LinearLayoutManager.VERTICAL, false)
        }
    }



}