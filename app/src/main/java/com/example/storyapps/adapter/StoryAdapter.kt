package com.example.storyapps.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapps.data.model.ListStory
import com.example.storyapps.databinding.ItemStoryBinding
import com.example.storyapps.ui.main.DetailActivity

class StoryAdapter:
    PagingDataAdapter<ListStory, StoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: ItemStoryBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ListStory) {
           with(binding) {
               tvUsername.text = data.name
               Glide.with(itemView)
                   .load(data.photoUrl)
                   .centerCrop()
                   .into(ivStory)
           }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context,  DetailActivity::class.java)
                intent.putExtra("detail", data)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivStory, "profile"),
                        Pair(binding.tvUsername, "name"),
                    )
                it.context.startActivity(intent, optionsCompat.toBundle())
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if(data != null) holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<ListStory>() {
            override fun areItemsTheSame(oldItem: ListStory, newItem: ListStory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStory, newItem: ListStory): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

}

