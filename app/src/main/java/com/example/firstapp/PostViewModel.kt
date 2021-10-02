package com.example.firstapp

import androidx.lifecycle.ViewModel

class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.data
    fun like() {
        repository.likeById()
    }
    fun share() {
        repository.shareById()
    }
}