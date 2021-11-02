package com.example.firstapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firstapp.dao.PostDao

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            var lastId = data.value?.firstOrNull()?.id
            if (lastId != null) {
                data.value = listOf(post.copy(id = lastId + 1)) + data.value.orEmpty()
            } else {
                data.value = listOf(post.copy(id = 1)) + data.value.orEmpty()
            }
            return
        }

        data.value = data.value?.map {
            if (it.id == post.id) {
                it.copy(content = post.content, likes = post.likes, shares = post.shares)
            } else {
                it
            }
        }
    }

    override fun removeById(id: Long) {
        data.value = data.value?.filter { it.id != id }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        data.value = data.value?.map {
            if (it.id != id) it else it.copy(
                content = it.content,
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )

        }
    }

    override fun shareById(id: Long) {
        data.value = data.value?.map {
            if (it.id != id) it else it.copy(
                content = it.content,
                sharedByMe = !it.sharedByMe,
                shares = if (it.sharedByMe) it.shares - 1 else it.shares + 1
            )
        }
    }
}