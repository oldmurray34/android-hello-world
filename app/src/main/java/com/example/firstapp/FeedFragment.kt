package com.example.firstapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.firstapp.NewPostFragment.Companion.contentArg
import com.example.firstapp.databinding.FragmentFeedBinding


fun getFormattedNumber(number: Int): String {
    val numberChars = number.toString().toCharArray().toCollection(ArrayList())
    val result = when (number) {
        in 1000..9999 -> "${numberChars[0]}.${numberChars[1]}K"
        in 10000..99999 -> "${numberChars[0]}${numberChars[1]}K"
        in 100000..999999 -> "${numberChars[0]}${numberChars[1]}${numberChars[2]}K"
        in 1000000..1099999 -> "${numberChars[0]}M"
        in 1100000..9999999 -> "${numberChars[0]}.${numberChars[1]}M"
        in 10000000..99999999 -> "${numberChars[0]}${numberChars[1]}.${numberChars[2]}M"
        else -> number.toString()
    }
    return result
}



class FeedFragment : Fragment() {
    val viewModel: PostViewModel by activityViewModels()
//    private val launcherEdit = registerForActivityResult(EditPostActivityContract()) { text ->
//        text ?: return@registerForActivityResult
//        viewModel.changeContent(text.toString())
//        viewModel.save()
//    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = PostsAdapter (
            object : OnActionListener {
                override fun onEditClicked(post: Post) {
                    viewModel.edit(post)
                    findNavController().navigate(R.id.action_feedFragment_to_editPostFragment)
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
                    findNavController().navigate(
                        R.id.action_feedFragment_to_postFragment, bundleOf("postId" to post.id)
                    )
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
        binding.posts.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }
//        viewModel.edited.observe(viewLifecycleOwner) {
//            if (it.id == 0L) {
//                return@observe
//            }
//            launcherEdit.launch(it.content)
//        }

        binding.newPost.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment2)
        }

        return binding.root
    }
}