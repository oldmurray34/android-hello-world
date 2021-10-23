package com.example.firstapp

import android.net.Uri


data class Post(
    val id: Long = 0L,
    val author: String = "Me",
    val content: String = "",
    val published: String = "01.01.1970",
    val likedByMe: Boolean = false,
    val sharedByMe: Boolean = false,
    val likes: Int = 0,
    val shares: Int = 0,
    val views: Int = 0,
    val videoId: String? = null
)

