package ru.netology.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likeCount: Int,
    val shareCount: Int,
    val viewCount: Int,
    val liked: Boolean = false,
    val shared: Boolean = false
)