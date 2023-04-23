package com.example.carrot_market_compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.carrot_market_compose.ui.theme.CarrotmarketcomposeTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun CarrotApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Home(
                posts = fakePostList,
                onMenuClick = {},
                onNoticeClick = {},
                onPostClick = {},
                onSearchClick = {}
            )
        }
    }

}

data class Post(
    val id: String,
    val title: String,
    val imgUrl: String,
    val location: String,
    val createdTime: String,
    val price: String,
    val likeCount: Int
)

@Composable
fun Home(
    posts: Flow<PagingData<Post>>,
    onNoticeClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit,
    onPostClick: (Post) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.background(color = Color.White),
        topBar = {
            HomeTobBar(onNoticeClick = onNoticeClick)
        }
    ) { padding ->
        val pagingItems: LazyPagingItems<Post> = posts.collectAsLazyPagingItems()
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.card_side_margin))
        ) {
            items(
                count = pagingItems.itemCount,
                key = { index ->
                    val post = pagingItems[index]
                    post?.id ?: ""
                }
            ) { index ->
                val photo = pagingItems[index] ?: return@items
                PostListItem(post = photo) {
                    onPostClick(photo)
                }
                Divider(color = Color.DarkGray)
            }
        }
    }
}


@Composable
fun PostListItem(
    post: Post,
    onPostClick: (Post) -> Unit
) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(all = 10.dp)
    ) {
        AsyncImage(
            model = post.imgUrl,
            placeholder = ColorPainter(Color.LightGray),
            contentDescription = null,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.post_item_size))
                .width(dimensionResource(id = R.dimen.post_item_size))
                .clip(RoundedCornerShape(size = 10.dp)),
            contentScale = ContentScale.Crop,

            )
        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(
                text = post.title,
                maxLines = 2,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row {
                Text(
                    text = post.location,
                    maxLines = 1,
                    modifier = Modifier
                        .wrapContentSize(),
                    fontSize = 14.sp,
                    color = Color.DarkGray

                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = post.createdTime,
                    maxLines = 1,
                    modifier = Modifier
                        .wrapContentSize(),
                    fontSize = 14.sp,
                    color = Color.DarkGray

                )
            }
            Text(
                text = post.price,
                maxLines = 1,
                modifier = Modifier.wrapContentSize(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Row(
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    tint = Color.DarkGray,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = post.likeCount.toString(),
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun HomeTobBar(
    onNoticeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text("당근마켓")
        },
        modifier = modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onNoticeClick) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
    )
}


private val fakePost = Post(
    id = "1",
    title = "안녕하세요",
    imgUrl = "https://www.itworld.co.kr/files/itworld/ITW_202207_01/14-inch-macbook-pro-2-100912448-orig-1-jpg.webp",
    location = "관악구",
    createdTime = "1초전",
    price = "1,2000원",
    likeCount = 2
)

private val fakePostList = flowOf(
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

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    CarrotmarketcomposeTheme {
        Home(posts = fakePostList,
            onPostClick = {},
            onMenuClick = {},
            onNoticeClick = {},
            onSearchClick = {})
    }

}


@Preview(showBackground = true)
@Composable
private fun PostListItemPreview() {
    CarrotmarketcomposeTheme {
        PostListItem(post = fakePost, onPostClick = {})
    }

}
