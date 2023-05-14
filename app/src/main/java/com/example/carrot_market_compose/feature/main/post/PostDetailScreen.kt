package com.example.carrot_market_compose.feature.main.post

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.carrot_market_compose.R
import com.example.carrot_market_compose.feature.main.data.Post


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostDetailScreen(
    post: Post
) {
    Scaffold(
        modifier = Modifier.background(color = Color.White),
        topBar = {
            PostDetailTopBar()
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            HorizontalPager(pageCount = post.imgUrl.size) { index ->
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = post.imgUrl.getOrNull(index),
                    contentDescription = null
                )
            }
            Row {
                AsyncImage(
                    model = post.writer.profileImgUrl,
                    placeholder = ColorPainter(Color.LightGray),
                    contentDescription = null,
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.profile_image_size))
                        .width(dimensionResource(id = R.dimen.profile_image_size))
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column {
                    Text(
                        text = post.writer.name,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = post.writer.location,
                        maxLines = 1,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun PostDetailTopBar() {

}