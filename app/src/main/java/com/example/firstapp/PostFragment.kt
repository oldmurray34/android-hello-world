package com.example.firstapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.firstapp.databinding.FragmentPostBinding

class PostFragment : Fragment(R.layout.fragment_post) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by activityViewModels()

        val passedId: Long = arguments?.getLong("postId") ?: 0

        fun updateToChosen () {
            viewModel.data.value?.find { it.id == passedId }?.let { viewModel.chosenPost(it) }
        }
        updateToChosen()
        viewModel.chosen.observe(viewLifecycleOwner) { post ->
            with(binding) {
                authorName.text = post.author
                content.text = post.content
                published.text = post.published
                likes.text = getFormattedNumber(post.likes)
                share.text = getFormattedNumber(post.shares)
                views.text = post.views.toString()
                likes.isChecked = post.likedByMe
                share.isChecked = post.sharedByMe

                likes.setOnClickListener {
                    viewModel.likeById(post.id)
                    updateToChosen()
                }

                if (post.videoId == null) binding.video.visibility = View.GONE
                binding.video.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoId))
                    startActivity(intent)
                }

                share.setOnClickListener {
                    viewModel.shareById(post.id)
                    updateToChosen()
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }
                    startActivity(intent)
                }

                menu.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.post_menu)
                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.menu_remove -> {
                                    viewModel.removeById(post.id)
                                    activity?.onBackPressed()
                                    true
                                }
                                R.id.menu_edit -> {
                                    viewModel.edit(post)
                                    findNavController().navigate(R.id.action_postFragment_to_editPostFragment)
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
        return binding.root
    }
}
