package com.example.carrot_market_compose.data

import androidx.paging.PagingData
import com.example.carrot_market_compose.feature.main.data.Post
import com.example.carrot_market_compose.feature.main.data.User
import kotlinx.coroutines.flow.flowOf

val fakeUser = User(
    id = "1",
    name = "김민수",
    location = "서울시 강남구",
    profileImgUrl = "https://www.itworld.co.kr/files/itworld/ITW_202207_01/14-inch-macbook-pro-2-100912448-orig-1-jpg.webp"
)

val fakePost = Post(
    id = "1",
    title = "안녕하세요",
    imgUrl = listOf("https://www.itworld.co.kr/files/itworld/ITW_202207_01/14-inch-macbook-pro-2-100912448-orig-1-jpg.webp"),
    createdTime = "1초전",
    price = "1,2000원",
    likeCount = 2,
    isLike = true,
    chatCount = 3,
    viewCount = 4,
    mannerTemperature = 5,
    writer = fakeUser,
    location = Location(fakeUser.location)
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