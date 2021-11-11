package com.example.firstapp

import android.app.Application
import androidx.lifecycle.*
import com.example.firstapp.db.AppDb

private val empty = Post ()

class PostViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )
    val data = repository.getAll()
    private val _edited = MutableLiveData(empty)
    val edited: LiveData<Post>
        get() = _edited
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun changeContent(content: String) {
        _edited.value = _edited.value?.copy(content = content)
    }
    fun save() {
        _edited.value?.also {
            repository.save(it)
            _edited.value = empty
        }
    }

    fun edit(post: Post) {
        _edited.value = post
    }

    fun chosenPost(id: Long): LiveData<Post?> = data.map { posts ->
        posts.find { it.id == id }
    }
}