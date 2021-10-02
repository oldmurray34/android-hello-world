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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter {
            viewModel.likeById(it.id)
        }
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }
}