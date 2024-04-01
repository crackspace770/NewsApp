package com.fajar.weathernews.ui.tech

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajar.weathernews.R
import com.fajar.weathernews.data.Result
import com.fajar.weathernews.data.adapter.NewsAdapter
import com.fajar.weathernews.data.utils.HorizontalSpaceItemDecoration
import com.fajar.weathernews.data.utils.ViewModelFactory
import com.fajar.weathernews.databinding.FragmentTechBinding
import com.fajar.weathernews.ui.detail.NewsDetailActivity

class TechNewsFragment:Fragment() {

    private lateinit var binding: FragmentTechBinding
    private val newsAdapter by lazy { NewsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTechBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val viewModel:TechViewModel by viewModels {
            factory
        }

        newsAdapter.onItemClick = { news->
            val intent = Intent(requireContext(), NewsDetailActivity::class.java)
            intent.putExtra(NewsDetailActivity.EXTRA_NEWS, news) // Pass the surah number
            startActivity(intent)
        }



        viewModel.getTechHeadline().observe(requireActivity()) { result ->

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




        val horizontalSpacingInPixels = resources.getDimensionPixelSize(R.dimen.vertical_spacing) // Define your desired spacing dimension
        val itemDecoration = HorizontalSpaceItemDecoration(horizontalSpacingInPixels)
        binding.rvTechNews.addItemDecoration(itemDecoration)

        rvTechNews()

    }

    private fun rvTechNews() {
        binding.rvTechNews.apply {
            adapter = newsAdapter // Change this line
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

}