package com.example.carrot_market_compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun CarrotApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Home(
                posts = flowOf(
                    PagingData.from(
                        listOf(
                            fakePost.copy(),
                            fakePost.copy(),
                            fakePost.copy(),
                            fakePost.copy()
                        )
                    )
                ),
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
    val imgUrl: String
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
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PostListItem(
    post: Post,
    onPostClick: (Post) -> Unit
) {
    Row(Modifier.fillMaxSize()) {
        GlideImage(
            model = post.imgUrl,
            contentDescription = null,
            Modifier
                .fillMaxHeight()
                .width(dimensionResource(id = R.dimen.plant_item_image_height)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = post.title,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.margin_normal))
        )
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
    id = "",
    title = "안녕하세요",
    imgUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fko.wikipedia.org%2Fwiki%2F%25EC%2582%25AC%25EA%25B3%25BC_%25ED%258C%258C%25EC%259D%25B4&psig=AOvVaw2bgBTtg4-ZVYRtePtPZ5Mm&ust=1682248174072000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCNCtkfmsvf4CFQAAAAAdAAAAABAD"
)

@Preview
@Composable
private fun PostListItemPreview() {
    PostListItem(post = fakePost, onPostClick = {})
}
