package com.example.firstapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
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
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) {
            post -> with(binding) {
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
        }
    }
}