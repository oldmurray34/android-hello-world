package com.example.firstapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.firstapp.dao.PostDao
import com.example.firstapp.entity.PostEntity

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {

    override fun getAll(): LiveData<List<Post>> = dao.getAll()
        .map { it.map(PostEntity::toDto) }

    override fun save(post: Post) {
        dao.insert(PostEntity.fromDto(post))
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
    }
}