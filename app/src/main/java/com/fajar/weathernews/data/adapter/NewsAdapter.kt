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
import com.fajar.weathernews.databinding.NewsItemBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class NewsAdapter : ListAdapter<NewsEntity, NewsAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((NewsEntity) -> Unit?)? = null

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bindings = NewsItemBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(data: NewsEntity) {
            with(bindings) {
                val source = data.source

                tvTitle.text = data.title
                tvSource.text = "Source: $source"

                // Format the date string
                val formattedDate = formatDate(data.publishedAt)
                tvDate.text = formattedDate

                if (!data.urlToImage.isNullOrEmpty()) {
                    Glide.with(itemView.context)
                        .load(data.urlToImage)
                        .centerCrop()
                        .into(imgThumbnail)
                } else {
                    // If urlToImage is null or empty, load a placeholder image
                    imgThumbnail.setImageResource(R.drawable.placeholder)
                }
            }
        }

        init {
            bindings.root.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition))
            }
        }
    }

    // Function to format date string
    private fun formatDate(dateString: String): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        try {
            val date = inputFormat.parse(dateString)
            return date?.let { outputFormat.format(it) }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsEntity>() {
            override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}