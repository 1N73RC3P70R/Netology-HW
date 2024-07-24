package ru.netology

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.dto.Post
import ru.netology.repository.PostRepository
import ru.netology.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likeCount = 0,
    shareCount = 0,
    viewCount = 0,
    liked = false,
    published = ""
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data: LiveData<List<Post>> = repository.getAll()
    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState> = _dataState
    val edited = MutableLiveData(empty)
    private val _postEditing = MutableLiveData(false)
    val postEditing: LiveData<Boolean> = _postEditing
    private var deletedWhileEditing = false

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)

    fun cancelEditing() {
        edited.value = empty
        _postEditing.value = false
        deletedWhileEditing = false
    }

    fun startEditing(post: Post) {
        edited.value = post
        _postEditing.value = true
        deletedWhileEditing = false
    }

    fun save() {
        edited.value?.let {
            if (!deletedWhileEditing) {
                repository.save(it)
            }
        }
        edited.value = empty
        _postEditing.value = false
        deletedWhileEditing = false
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun removeById(id: Long) {
        repository.removeById(id)
        if (edited.value?.id == id) {
            deletedWhileEditing = true
        }
    }
}

enum class FeedModelState {
    IDLE, LOADING, ERROR
}