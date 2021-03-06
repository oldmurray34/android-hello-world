package com.example.firstapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostRepositorySharedPrefsImpl(
    context: Context
) : PostRepository {

    private val gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"

    private var postId = 0L
    private var posts = emptyList<Post>()

    private val data = MutableLiveData(posts)

    init {
        prefs.getString(key, null)?.let{
            posts = gson.fromJson(it, type)
            data.value = posts
        }
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            data.value = listOf(post.copy(id = postId++)) + data.value.orEmpty()
            sync()
            return
        }

        data.value = data.value?.map {
            if (it.id == post.id) {
                it.copy(content = post.content, likes = post.likes, shares = post.shares)
            } else {
                it
            }
        }
        sync()
    }

    override fun removeById(id: Long) {
        data.value = data.value?.filter { it.id != id }
        sync()
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
        sync()
    }

    override fun shareById(id: Long) {
        data.value = data.value?.map {
            if (it.id != id) it else it.copy(
                content = it.content,
                sharedByMe = !it.sharedByMe,
                shares = if (it. sharedByMe) it.shares - 1 else it.shares + 1
            )
        }
        sync()
    }

    private fun sync() {
        with(prefs.edit()) {
            putString(key, gson.toJson(posts))
            apply()
        }
    }
}