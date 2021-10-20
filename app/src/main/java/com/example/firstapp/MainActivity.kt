package com.example.firstapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.firstapp.databinding.ActivityMainBinding

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



class MainActivity : AppCompatActivity() {
    val viewModel: PostViewModel by viewModels()
    private val launcherEdit = registerForActivityResult(EditPostActivityContract()) { text ->
        text ?: return@registerForActivityResult
        viewModel.changeContent(text.toString())
        viewModel.save()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter (
            object : OnActionListener {
                override fun onEditClicked(post: Post) {
                    viewModel.edit(post)

                }

                override fun onRemoveClicked(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onLikeClicked(post: Post) {
                    viewModel.likeById(post.id)
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
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                return@observe
            }
            launcherEdit.launch(it.content)
        }

        val launcher = registerForActivityResult(NewPostActivityContract()) { text ->
            text ?: return@registerForActivityResult
            viewModel.changeContent(text.toString())
            viewModel.save()
        }
        binding.newPost.setOnClickListener {
            launcher.launch()
        }
    }
}