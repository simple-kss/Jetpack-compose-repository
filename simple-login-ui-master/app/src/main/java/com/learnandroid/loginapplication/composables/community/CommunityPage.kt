package com.learnandroid.loginapplication.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.learnandroid.loginapplication.ui.theme.whiteBackground

@Composable
fun CommunityPage(navController: NavController) {
    CommunityPageContents();
}

@Composable
fun CommunityPageContents() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Text(
                text = "홍길동님 안녕하세요.",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
            // 이거로 레이지컬럼 뿌려저야댐
            // https://www.youtube.com/watch?v=V-3sLO_TWl0&ab_channel=HoodLab
        }
    }
}

@Composable
@Preview
fun CommunityPageContentsPreview() {
    CommunityPageContents();
}