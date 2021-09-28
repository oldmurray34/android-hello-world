package com.example.firstapp

import androidx.lifecycle.LiveData

interface PostRepository {
    val data: LiveData<Post>
    fun like()
    fun share()
}