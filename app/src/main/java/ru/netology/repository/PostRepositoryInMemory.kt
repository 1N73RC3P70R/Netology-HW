package ru.netology.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likeCount = 999,
            shareCount = 999,
            viewCount = 999_999
        ),
        Post(
            id = nextId++,
            author = "Нетология.",
            content = "Университет интернет-профессий будущего!",
            published = "22 мая в 18:46",
            likeCount = 999,
            shareCount = 999,
            viewCount = 999_999
        ),
        Post(
            id = nextId++,
            author = "Нетология.",
            content = "https://www.youtube.com/watch?v=eMEH6LG9_iQ",
            published = "23 мая в 00:00",
            likeCount = 999,
            shareCount = 999,
            viewCount = 999_999
        )
    )
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Нетология.",
                    likeCount = 0,
                    shareCount = 0,
                    viewCount = 0,
                    published = "Только что"
                )
            ) + posts
        } else {
            posts = posts.map {
                if (it.id != post.id) it else it.copy(content = post.content)
            }
        }
        data.value = posts
    }

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likeCount = it.likeCount + if (it.liked) -1 else 1,
                liked = !it.liked
            )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shareCount = it.shareCount + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }
}