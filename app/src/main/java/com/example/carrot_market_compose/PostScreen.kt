package com.example.carrot_market_compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carrot_market_compose.ui.theme.CarrotmarketcomposeTheme


@Composable
fun PostScreen() {
    Scaffold(
        topBar = { PostTopAppBar() }
    ) { padding ->
        Box(modifier = Modifier.padding(padding))
    }
}

@Composable
private fun PostTopAppBar(
    modifier: Modifier = Modifier
) {
    Row {
        TopAppBar(
            elevation = 0.dp,
            backgroundColor = Color.White,
            modifier = modifier
                .statusBarsPadding()
                .padding(horizontal = 10.dp, vertical = 10.dp),
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostScreenPreview(){
    CarrotmarketcomposeTheme {
        PostScreen()
    }
}