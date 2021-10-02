package com.example.firstapp

import androidx.lifecycle.LiveData

interface PostRepository {
    val data: LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
}