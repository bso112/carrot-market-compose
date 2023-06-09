package com.example.carrot_market_compose.feature.main.data

import com.example.carrot_market_compose.data.Location


data class Post(
    val id: String,
    val title: String,
    val writer : User,
    val imgUrl: List<String>,
    val createdTime: String,
    val price: String,
    val isLike: Boolean,
    val likeCount: Int,
    val chatCount: Int,
    val viewCount: Int,
    val mannerTemperature: Int,
    val location : Location
)