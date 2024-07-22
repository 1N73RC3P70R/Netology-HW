package ru.netology

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.dto.Post
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl
import ru.netology.repository.PostRepository

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data: LiveData<Post> = repository.get()

    fun like() {
        repository.like()
    }

    fun share() {
        repository.share()
    }


    fun formatCount(count: Int): String {
        return when {
            count < 1000 -> count.toString()
            count < 10000 -> {
                if (count < 1100) "${count / 1000}K"
                else String.format("%.1fK", count / 1000.0)
            }

            count < 1_000_000 -> "${count / 1000}K"
            count < 10_000_000 -> {
                val millions = count / 1_000_000
                val remainder = (count % 1_000_000) / 100_000
                if (remainder == 0) "${millions}M" else "$millions.${remainder}M"
            }

            else -> "${count / 1_000_000}M"
        }
    }
}