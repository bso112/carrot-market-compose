package com.example.carrot_market_compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.carrot_market_compose.feature.main.data.TopLevelDestination
import com.example.carrot_market_compose.feature.main.navigateHome

@Composable
fun rememberCarrotAppState(
    navController: NavHostController = rememberNavController()
): CarrotAppState {
    return remember(
        navController
    ) {
        CarrotAppState(
            navController = navController
        )
    }
}

class CarrotAppState(
    val navController: NavHostController
) {
    fun navigateToTopLevelDestination(destination: TopLevelDestination) {
        when (destination) {
            TopLevelDestination.HOME -> navController.navigateHome()
            TopLevelDestination.NEARBY -> navController.navigate("nearby")
            TopLevelDestination.MY -> navController.navigate("my")
            TopLevelDestination.TOWN_LIFE -> navController.navigate("town_life")
            TopLevelDestination.CHAT -> navController.navigate("chat")
        }
    }
}