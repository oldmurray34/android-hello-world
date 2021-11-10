package com.example.firstapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.firstapp.Post

@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
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
) {

    companion object {
        fun fromDto(dto: Post): PostEntity = with(dto) {
            PostEntity(
                id = id,
                author = author,
                content = content,
                published = published,
                likedByMe = likedByMe,
                sharedByMe = sharedByMe,
                likes = likes,
                shares = shares,
                views = views,
                videoId = videoId
            )
        }
    }

    fun toDto(): Post = with(this) {
        Post(
            id = id,
            author = author,
            content = content,
            published = published,
            likedByMe = likedByMe,
            sharedByMe = sharedByMe,
            likes = likes,
            shares = shares,
            views = views,
            videoId = videoId
        )
    }
}