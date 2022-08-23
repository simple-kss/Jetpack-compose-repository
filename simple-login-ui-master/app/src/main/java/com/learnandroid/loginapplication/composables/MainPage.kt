package com.learnandroid.loginapplication.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.learnandroid.loginapplication.MainViewModel
import com.learnandroid.loginapplication.ui.theme.whiteBackground

@Composable
fun MainPage(navController: NavController) {
//    val image = imageResource(id = R.drawable.login_image)
    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(30.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight(0.90f),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.80f)
                        .height(50.dp),
                    text = "홍길동님 안녕하세요.",
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                )
                // Box Column Text Spacer button
                Button(
                    onClick = {
                        navController.navigate("mypage_page")
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxWidth(0.80f)
                        .height(100.dp),
                    shape = RoundedCornerShape(25),
                ) {
                    Text(
                        text = "마이페이지",
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        textAlign = TextAlign.Left,
                        fontSize = 30.sp,
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Button(
                    onClick = {
                        navController.navigate("community_page")
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxWidth(0.80f)
                        .height(100.dp),
                    shape = RoundedCornerShape(25),
                ) {
                    Text(
                        text = "커뮤니티",
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        textAlign = TextAlign.Left,
                        fontSize = 30.sp,
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Button(
                    onClick = {
                        navController.navigate("job_search_info_page")
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxWidth(0.80f)
                        .height(100.dp),
                    shape = RoundedCornerShape(25),
                ) {
                    Text(
                        text = "구직정보",
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        textAlign = TextAlign.Left,
                        fontSize = 30.sp,
                    )
                }
            }
        }
    }
}
