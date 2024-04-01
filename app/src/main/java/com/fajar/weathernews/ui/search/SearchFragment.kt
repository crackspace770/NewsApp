package com.fajar.weathernews.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajar.weathernews.R
import com.fajar.weathernews.data.Result
import com.fajar.weathernews.data.adapter.NewsAdapter
import com.fajar.weathernews.data.utils.HorizontalSpaceItemDecoration
import com.fajar.weathernews.data.utils.ViewModelFactory
import com.fajar.weathernews.databinding.FragmentSearchBinding
import com.fajar.weathernews.ui.detail.NewsDetailActivity

class SearchFragment: Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private val newsAdapter by lazy { NewsAdapter() }
    private lateinit var factory: ViewModelFactory
    private val viewModel: SearchViewModel by viewModels {
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        factory = ViewModelFactory.getInstance(requireContext())

        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)


        val horizontalSpacingInPixels = resources.getDimensionPixelSize(R.dimen.vertical_spacing) // Define your desired spacing dimension
        val itemDecoration = HorizontalSpaceItemDecoration(horizontalSpacingInPixels)
        binding.rvSearch.addItemDecoration(itemDecoration)

        rvSearch()
        observeSearchResult()

    }

    private fun rvSearch() {
        binding.rvSearch.apply {
            adapter = newsAdapter // Change this line
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        newsAdapter.onItemClick = { news->
            val intent = Intent(requireContext(), NewsDetailActivity::class.java)
            intent.putExtra(NewsDetailActivity.EXTRA_NEWS, news) // Pass the surah number
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.search)
        search.expandActionView()

        val searchView = search.actionView as SearchView

        searchView.apply {
            onActionViewExpanded()
            setIconifiedByDefault(false)
            isFocusable = true
            isIconified = false
            requestFocusFromTouch()
            requestFocus()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    clearFocus()
                    binding.rvSearch.scrollToPosition(0)
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) {
                        binding.apply {
                            progressBar.visibility = View.VISIBLE
                            viewError.root.visibility = View.GONE
                            viewEmpty.root.visibility = View.GONE
                            onInitialSearchStateMessage.visibility = View.GONE
                            rvSearch.scrollToPosition(0)
                        }
                        viewModel.setSearchQuery(query)
                        observeSearchResult()
                    } else if (query.equals("")) {

                    }
                    return true

                }
            } )
        }

    }

    private fun observeSearchResult() {
        binding.apply {
            viewModel.searchResult.observe(viewLifecycleOwner) { results ->
                if (results != null) {
                    when (results) {
                        is Result.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            val searchResult = results.data
                            progressBar.visibility = View.GONE
                            // Set data to adapter
                            newsAdapter.submitList(searchResult)
                            rvSearch.scrollToPosition(0)
                            if (searchResult.isEmpty()) {
                                viewEmpty.root.visibility = View.VISIBLE
                                onInitialSearchStateMessage.visibility = View.GONE
                            }
                        }
                        is Result.Error -> {
                            progressBar.visibility = View.GONE
                            viewError.root.visibility = View.VISIBLE
                        }
                        else -> {
                            // Handle other cases if needed
                        }
                    }
                }
            }
        }
    }

}