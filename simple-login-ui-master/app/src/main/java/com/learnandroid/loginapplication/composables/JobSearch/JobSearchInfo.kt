package com.learnandroid.loginapplication.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learnandroid.loginapplication.ui.theme.whiteBackground
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.learnandroid.loginapplication.MainViewModel
import com.learnandroid.loginapplication.SearchWidgetState
import com.learnandroid.loginapplication.ui.theme.primaryColor
import com.learnandroid.loginapplication.ui.theme.uGray
import com.learnandroid.loginapplication.ui.theme.uGray1

// Scaffold는 상단 앱바나 하단 앱바의 슬롯을 제공합니다. 컴포저블의 배치는 내부적으로 처리됩니다.

// scaffold 개념 정리 잘되어있는 곳 :https://mypark.tistory.com/entry/JETPACK-COMPOSE-Scaffold-%EC%A0%95%EB%A6%AC
@Composable
fun JobSearchInfo(mainViewModel: MainViewModel, navController: NavController)  {

    val searchWidgetState by mainViewModel.searchWidgetState
    val searchTextState by mainViewModel.searchTextState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground),
    ) {
        Text(
            text = "구직정보 검색",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )

        MainAppBar(
            searchWidgetState = searchWidgetState,
            searchTextState = searchTextState,
            onTextChange = {
                mainViewModel.updateSearchTextState(newValue = it)
            },
            onCloseClicked = {
                mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
            },
            onSearchClicked = {
                Log.d("Searched Text", it)
            },
            onSearchTriggered = {
                mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
            }
        )

        Column() {
            Row() {
                Text(text = "123")
                Text(text = "12412241")
                Text(text = "요구자격증")
                Text(text = "정보처리기사")
            }
            Row() {
                Text(text = "신한은행")
                Text(text = "요구자격증")
                Text(text = "정보처리기사")
            }
            Row() {
                Text(text = "신한은행")
                Text(text = "요구자격증")
                Text(text = "정보처리기사")
            }
        }
    }


//    // Scaffold는 상단 앱바나 하단 앱바의 슬롯을 제공합니다. 컴포저블의 배치는 내부적으로 처리됩니다.
//    Scaffold(
//        // top 검색 바 생성
//        topBar = {
//            MainAppBar(
//                searchWidgetState = searchWidgetState,
//                searchTextState = searchTextState,
//                onTextChange = {
//                    mainViewModel.updateSearchTextState(newValue = it)
//                },
//                onCloseClicked = {
//                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
//                },
//                onSearchClicked = {
//                    Log.d("Searched Text", it)
//                },
//                onSearchTriggered = {
//                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
//                }
//            )
//        }
//    ) {}
}

// 출처 : https://www.youtube.com/watch?v=3oXBnM6fZj0&t=224s&ab_channel=Stevdza-San
@Composable
fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        backgroundColor = uGray,
        title = {
            Text(
                text = "검색",
                color = uGray1
            )
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        },
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(primaryColor)
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = primaryColor
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ))
    }
}

@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(onSearchClicked = {})
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Some random text",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}