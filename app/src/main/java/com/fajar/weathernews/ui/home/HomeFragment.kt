package com.fajar.weathernews.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fajar.weathernews.data.adapter.NewsPagerAdapter
import com.fajar.weathernews.databinding.FragmentHomeBinding
import com.fajar.weathernews.ui.news.NewsFragment
import com.fajar.weathernews.ui.science.ScienceFragment
import com.fajar.weathernews.ui.tech.TechNewsFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment: Fragment() {

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragment = arrayListOf<Fragment>(
            NewsFragment(),
            TechNewsFragment(),
            ScienceFragment()

        )

        binding?.viewpagerHome?.isUserInputEnabled = false

        val viewPagerAdapter = NewsPagerAdapter(categoriesFragment,childFragmentManager,lifecycle)
        binding?.viewpagerHome?.adapter = viewPagerAdapter
        binding?.let {
            TabLayoutMediator(it.tabLayout, binding!!.viewpagerHome) { tab, position ->
                when (position) {
                    0 -> tab.text = "Trending"
                    1 -> tab.text = "Tech"
                    2 -> tab.text = "Science"


                }
            }.attach()
        }


    }
}