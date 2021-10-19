package com.example.firstapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.databinding.CardPostBinding

interface OnActionListener {
    fun onEditClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
}


class PostsAdapter(
    private val actionListener: OnActionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, actionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val actionListener: OnActionListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            authorName.text = post.author
            content.text = post.content
            published.text = post.published
            likes.text = getFormattedNumber(post.likes)
            share.text = getFormattedNumber(post.shares)
            views.text = post.views.toString()
            likes.isChecked = post.likedByMe
            share.isChecked = post.sharedByMe
            likes.setOnClickListener {
                actionListener.onLikeClicked(post)
            }

            share.setOnClickListener {
                actionListener.onShareClicked(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                   inflate(R.menu.post_menu)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.menu_remove -> {
                                actionListener.onRemoveClicked(post)
                                true
                            }
                            R.id.menu_edit -> {
                                actionListener.onEditClicked(post)
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
