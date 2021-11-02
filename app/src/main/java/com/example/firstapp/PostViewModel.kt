package com.example.firstapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firstapp.db.AppDb

private val empty = Post ()

class PostViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )
    val data = repository.getAll()
    private val _edited = MutableLiveData(empty)
    private val _chosen = MutableLiveData(empty)
    val edited: LiveData<Post>
        get() = _edited
    val chosen: LiveData<Post>
        get() = _chosen
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

    fun chosenPost(id: Long) {
        _chosen.value = data.value?.find { it.id == id }
    }
}