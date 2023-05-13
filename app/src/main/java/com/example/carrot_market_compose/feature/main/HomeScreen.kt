package com.example.carrot_market_compose.feature.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.carrot_market_compose.R
import com.example.carrot_market_compose.data.Location
import com.example.carrot_market_compose.data.Post
import com.example.carrot_market_compose.data.fakePost
import com.example.carrot_market_compose.data.fakePostList
import com.example.carrot_market_compose.design.dim
import com.example.carrot_market_compose.feature.main.data.BottomNavType
import com.example.carrot_market_compose.noRippleClickable
import com.example.carrot_market_compose.ui.theme.CarrotmarketcomposeTheme
import kotlinx.coroutines.flow.Flow


@Composable
fun HomeScreen(
    posts: Flow<PagingData<Post>>,
    onNoticeClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit,
    onPostClick: (Post) -> Unit = {}
) {
    var isShowDim by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.background(color = Color.White),
        topBar = {
            HomeTobBar(
                onShowDropDown = { isShowDim = true },
                onDismissDropDown = { isShowDim = false },
                onNoticeClick = onNoticeClick,
                onMenuClick = onMenuClick,
                onSearchClick = onSearchClick,
                locations = listOf(Location("신림동"), Location("강서구"))
            )
        },
        bottomBar = {
            HomeBottomNavigation()
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
                val post = pagingItems[index] ?: return@items
                PostListItem(post = post, onPostClick = { onPostClick(post) })
                Divider(color = Color.LightGray)
            }
        }
    }

    DimScreen(isShowDim = isShowDim)
}


@Composable
fun HomeBottomNavigation() {
    var selectedItemIndex by remember { mutableStateOf(0) }
    BottomNavigation {
        BottomNavType.values().mapIndexed { index, type ->
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
                }
            )
        }
    }
}

@Composable
fun DimScreen(
    isShowDim: Boolean
) {
    val alpha: Float by animateFloatAsState(
        targetValue = if (isShowDim) 1f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(alpha = alpha)
            .background(color = Color.dim)
    )
}

@Composable
fun PostListItem(
    post: Post,
    onPostClick: (Post) -> Unit,
) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(all = 10.dp)
            .noRippleClickable {
                onPostClick(post)
            }
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
    locations: List<Location>,
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit,
    onNoticeClick: () -> Unit,
    onShowDropDown: () -> Unit,
    onDismissDropDown: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        TopAppBar(
            elevation = 0.dp,
            backgroundColor = Color.White,
            modifier = modifier
                .statusBarsPadding()
                .padding(horizontal = 10.dp, vertical = 10.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                DropDown(
                    locations = locations,
                    onShowDropDown = onShowDropDown,
                    onDismissDropDown = onDismissDropDown
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onSearchClick) {
                    Icon(Icons.Outlined.Search, contentDescription = null)
                }
                IconButton(onClick = onMenuClick) {
                    Icon(Icons.Outlined.Menu, contentDescription = null)
                }
                IconButton(onClick = onNoticeClick) {
                    Icon(Icons.Outlined.Notifications, contentDescription = null)
                }
            }
        }
        Divider(color = Color.LightGray)
    }
}


@Composable
private fun DropDown(
    locations: List<Location>,
    onShowDropDown: () -> Unit,
    onDismissDropDown: () -> Unit,
) {
    var showPopup by remember { mutableStateOf(false) }
    val selectedIndex = remember { mutableStateOf(0) }


    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.noRippleClickable { showPopup = true }
        ) {
            Text(
                text = locations[selectedIndex.value].name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            DropDownIcon(showPopup)
        }
        if (showPopup) {
            onShowDropDown()
            Box(
                modifier = Modifier.wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                DropdownMenu(
                    offset = DpOffset(x = 0.dp, y = 10.dp),
                    expanded = true,
                    onDismissRequest = { showPopup = false }
                ) {
                    locations.forEachIndexed { index, item ->
                        DropdownMenuItem(onClick = {
                            selectedIndex.value = index
                            showPopup = false
                        }) {
                            Text(
                                text = item.name,
                                style = TextStyle(
                                    color = if (index == selectedIndex.value) Color.Black else Color.DarkGray,
                                    fontWeight = if (index == selectedIndex.value) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        }
                    }
                }
            }
        } else {
            onDismissDropDown()
        }
    }
}

@Composable
private fun DropDownIcon(isShowDropDown: Boolean) {
    val degree: Float by animateFloatAsState(
        targetValue = if (isShowDropDown) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = Modifier
            .wrapContentSize()
            .rotate(degree)
    ) {
        Icon(Icons.Outlined.ArrowDropDown, contentDescription = null)
    }

}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    CarrotmarketcomposeTheme {
        HomeScreen(posts = fakePostList,
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


