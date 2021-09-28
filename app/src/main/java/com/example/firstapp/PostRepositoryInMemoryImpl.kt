package com.example.firstapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryInMemoryImpl: PostRepository {

    companion object {
        val defaultPost = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработе, аналитике и управвлнию. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен - http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likes = 1499,
            shares = 15
        )
    }

    override val data = MutableLiveData(defaultPost)

    override fun like() {
        var currentPost = data.value ?: return
        currentPost = if (currentPost.likedByMe) {
            currentPost.copy(
                likes = currentPost.likes - 1
            )
        } else {
            currentPost.copy(
                likes = currentPost.likes + 1
            )
        }
        data.value = currentPost.copy(
            likedByMe = !currentPost.likedByMe
        )
    }

    override fun share() {
        var currentPost = data.value ?: return
        currentPost = if (currentPost.sharedByMe) {
            currentPost.copy(
                shares = currentPost.shares - 1
            )
        } else {
            currentPost.copy(
                shares = currentPost.shares + 1
            )
        }
        data.value = currentPost.copy(
            sharedByMe = !currentPost.sharedByMe
        )
    }
}