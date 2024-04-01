package com.fajar.weathernews.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajar.weathernews.R
import com.fajar.weathernews.data.adapter.NewsAdapter
import com.fajar.weathernews.data.utils.HorizontalSpaceItemDecoration
import com.fajar.weathernews.data.utils.ViewModelFactory
import com.fajar.weathernews.databinding.FragmentBookmarkBinding
import com.fajar.weathernews.ui.detail.NewsDetailActivity


class BookmarkFragment: Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private val newsAdapter by lazy {NewsAdapter()}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val viewModel: BookmarkViewModel by viewModels {
            factory
        }

        newsAdapter.onItemClick = { news->
            val intent = Intent(requireContext(), NewsDetailActivity::class.java)
            intent.putExtra(NewsDetailActivity.EXTRA_NEWS, news) // Pass the surah number
            startActivity(intent)
        }

        viewModel.getBookmarkedNews().observe(viewLifecycleOwner) { bookmarkedNews ->
            newsAdapter.submitList(bookmarkedNews)
            binding.progressBar.visibility = View.GONE
            binding.viewError.tvError.text = getString(R.string.no_data)
            binding.viewError.root.visibility =
                if (bookmarkedNews.isNotEmpty()) View.GONE else View.VISIBLE
        }


        val horizontalSpacingInPixels = resources.getDimensionPixelSize(R.dimen.vertical_spacing) // Define your desired spacing dimension
        val itemDecoration = HorizontalSpaceItemDecoration(horizontalSpacingInPixels)
        binding.rvBookmark.addItemDecoration(itemDecoration)

        rvBookmark()

    }

    private fun rvBookmark() {
        binding.rvBookmark.apply {
            adapter = newsAdapter // Change this line
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }



}