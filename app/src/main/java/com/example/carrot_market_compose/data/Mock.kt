package com.example.carrot_market_compose.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.flowOf


val fakePost = Post(
    id = "1",
    title = "안녕하세요",
    imgUrl = "https://www.itworld.co.kr/files/itworld/ITW_202207_01/14-inch-macbook-pro-2-100912448-orig-1-jpg.webp",
    location = "관악구",
    createdTime = "1초전",
    price = "1,2000원",
    likeCount = 2
)

val fakePostList = flowOf(
    PagingData.from(
        listOf(
            fakePost.copy(id = "1"),
            fakePost.copy(id = "2"),
            fakePost.copy(id = "3"),
            fakePost.copy(id = "4"),
            fakePost.copy(id = "5")
        )
    )
)