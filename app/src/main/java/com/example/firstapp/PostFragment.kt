package com.example.firstapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.firstapp.databinding.FragmentPostBinding

class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(layoutInflater)

        val viewModel: PostViewModel by activityViewModels()

        val adapter = PostsAdapter (
            object : OnActionListener {
                override fun onEditClicked(post: Post) {
                    viewModel.edit(post)

                }

                override fun onVideoClicked(post: Post) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoId))
                    val chooser = Intent.createChooser(intent, null)
                    startActivity(intent)
                }

                override fun onRemoveClicked(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onLikeClicked(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onContentClicked(post: Post) {
                    //findNavController().navigate(R.id.action_feedFragment_to_newPostFragment2)
                }

                override fun onShareClicked(post: Post) {
                    viewModel.shareById(post.id)
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }

                    val chooser = Intent.createChooser(intent, null)

                    startActivity(intent)
                }
            }
        )

        return binding.root
    }
}