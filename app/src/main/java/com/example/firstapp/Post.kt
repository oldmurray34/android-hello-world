package com.example.firstapp

class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var sharedByMe: Boolean = false,
    var likes: Int = 0,
    var shares: Int = 0,
    var views: Int = 0
)

