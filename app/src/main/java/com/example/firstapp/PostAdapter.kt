package com.example.firstapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.databinding.CardPostBinding

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit
typealias OnRemoveListener = (post: Post) -> Unit

class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener,
    private val onRemoveListener: OnRemoveListener,
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener, onRemoveListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener,
    private val onRemoveListener: OnRemoveListener,
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
            likes.setOnClickListener {
                onLikeListener(post)
            }

            shares.setOnClickListener {
                onShareListener(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                   inflate(R.menu.post_menu)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.menu_remove -> {
                                onRemoveListener(post)
                                true
                            }
                            else -> false
                        }
                    }
                    show()
                }
            }

        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}
