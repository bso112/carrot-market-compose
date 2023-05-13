package com.example.carrot_market_compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carrot_market_compose.data.fakePostList
import com.example.carrot_market_compose.feature.main.HomeScreen
import com.example.carrot_market_compose.feature.main.data.TopLevelDestination
import com.example.carrot_market_compose.feature.main.homeScreen
import com.example.carrot_market_compose.ui.theme.CarrotmarketcomposeTheme

@Composable
fun CarrotApp(
    appState: CarrotAppState = rememberCarrotAppState()
) {
    CarrotmarketcomposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            Scaffold(
                bottomBar = { HomeBottomNavigation(appState::navigateToTopLevelDestination) }
            ) { padding ->
                CarrotNavHost(padding, appState)
            }
        }
    }
}


@Composable
fun HomeBottomNavigation(
    onClickNavItem: (TopLevelDestination) -> Unit
) {
    var selectedItemIndex by remember { mutableStateOf(0) }
    BottomNavigation {
        TopLevelDestination.values().mapIndexed { index, type ->
            val isSelected = index == selectedItemIndex
            BottomNavigationItem(
                selected = isSelected,
                icon = {
                    Icon(
                        imageVector = if (isSelected) type.selectedImage else type.deselectedImage,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = type.title)
                },
                onClick = {
                    selectedItemIndex = index
                    onClickNavItem(type)
                }
            )
        }
    }
}

@Composable
fun CarrotNavHost(
    paddingValues: PaddingValues,
    appState: CarrotAppState
) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = appState.navController,
        startDestination = "home"
    ) {
        homeScreen()
    }

}
