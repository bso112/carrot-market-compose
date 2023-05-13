package com.example.carrot_market_compose.feature.main

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.carrot_market_compose.data.fakePost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf

@HiltViewModel
class HomeViewModel : ViewModel() {

    val posts = flowOf(
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
}