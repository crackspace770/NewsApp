package com.fajar.weathernews.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fajar.weathernews.R
import com.fajar.weathernews.data.local.entity.NewsEntity
import com.fajar.weathernews.data.remote.response.NewsResponse
import com.fajar.weathernews.databinding.UsNewsItemBinding

class UsNewsAdapter : ListAdapter<NewsEntity, UsNewsAdapter.ListViewHolder>(NewsAdapter.DIFF_CALLBACK) {

    var onItemClick: ((NewsEntity) -> Unit?)? = null

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bindings = UsNewsItemBinding.bind(itemView)
        fun bind(data: NewsEntity) {
            with(bindings) {
                tvNewsTitle.text = data.title

                if (!data.urlToImage.isNullOrEmpty()) {
                    Glide.with(itemView.context)
                        .load(data.urlToImage)
                        .centerCrop()
                        .into(imgUrl)
                } else {
                    // If urlToImage is null or empty, load a placeholder image
                    imgUrl.setImageResource(R.drawable.placeholder)
                }
            }
        }

        init {
            bindings.root.setOnClickListener {
                bindings.root.setOnClickListener { onItemClick?.invoke(getItem(adapterPosition)) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.us_news_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsEntity>() {
            override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem:NewsEntity, newItem: NewsEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}