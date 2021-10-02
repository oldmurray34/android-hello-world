package com.example.firstapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

typealias OnLikeListener = (post: Post) -> Unit

class PostsAdapter(
    private val onLikeListener: OnLikeListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            authorName.text = post.author
            content.text = post.content
            published.text = post.published
            likesCount.text = getFormattedNumber(post.likes)
            sharesCount.text = getFormattedNumber(post.shares)
            viewsCount.text = post.views.toString()
            if (post.likedByMe) {
                likes.setImageResource(R.drawable.ic_liked)
            } else {
                likes.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            if (post.sharedByMe) {
                shares.setImageResource(R.drawable.ic_shared)
            } else {
                shares.setImageResource(R.drawable.ic_baseline_share_24)
            }
        }
        binding.likes.setOnClickListener {
            viewModel.like()
        }

        binding.shares.setOnClickListener {
            viewModel.share()
        }

        class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }

        }
    }
}