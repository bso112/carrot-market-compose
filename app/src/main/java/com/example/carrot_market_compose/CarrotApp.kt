package com.example.carrot_market_compose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carrot_market_compose.data.fakePostList

@Composable
fun CarrotApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                posts = fakePostList,
                onMenuClick = {},
                onNoticeClick = {},
                onPostClick = {},
                onSearchClick = {}
            )
        }
    }

}
