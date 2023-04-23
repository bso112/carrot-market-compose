package com.example.carrot_market_compose.data


data class Post(
    val id: String,
    val title: String,
    val imgUrl: String,
    val location: String,
    val createdTime: String,
    val price: String,
    val likeCount: Int
)