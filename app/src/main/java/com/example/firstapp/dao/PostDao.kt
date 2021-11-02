package com.example.firstapp.dao

import com.example.firstapp.Post

interface PostDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun shareById(id: Long)
    fun likeById(id: Long)
    fun removeById(id: Long)
}