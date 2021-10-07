//package com.example.firstapp.activity
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.activity.viewModels
//import com.example.firstapp.viewmodel.PostViewModel
//import com.example.firstapp.adapter.PostsAdapter
//import com.example.firstapp.databinding.ActivityMainBinding
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val viewModel: PostViewModel by viewModels()
//        val adapter = PostsAdapter ({
//            viewModel.likeById(it.id)}, {viewModel.shareById(it.id)})
//        binding.list.adapter = adapter
//        viewModel.data.observe(this) { posts ->
//            adapter.submitList(posts)
//        }
//    }
//}