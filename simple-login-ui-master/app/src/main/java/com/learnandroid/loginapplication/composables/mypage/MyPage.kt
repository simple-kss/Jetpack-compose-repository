package com.learnandroid.loginapplication.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.learnandroid.loginapplication.ui.theme.primaryColor
import com.learnandroid.loginapplication.ui.theme.whiteBackground

@Composable
fun MyPage(navController: NavController) {
    contents(navController);
}

@Composable
fun contents(navController: NavController) {
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
            Column(
                Modifier.size(600.dp, 200.dp)
            ) {
                Button(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                        .border(
                            width = 0.dp,
                            color = primaryColor
                        )
                        .background(primaryColor)
                        .size(300.dp, 100.dp),
                    onClick = {},
                ) {
                    Text(
                        text = "정보 설정",
                        fontSize = 20.sp,
                        color = whiteBackground
                    )
                }

                Button(
                    modifier = Modifier
                        .clip(RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp))
                        .border(
                            width = 0.dp,
                            color = primaryColor
                        )
                        .background(primaryColor)
                        .size(300.dp, 100.dp),
                    onClick = {
                        navController.navigate("certificate_search")
                    },
                ) {
                    Text(
                        text = "자격증 검색",
                        fontSize = 20.sp,
                        color = whiteBackground
                    )
                }
            }

            Text(
                text = "취득 자격증 목록",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Column() {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(primaryColor)
                        .size(300.dp, 100.dp),
                ) {
                    Text(text = "2012년 2월 18일", fontSize = 20.sp, color = whiteBackground)
                    Text(text = "정보처리기사", fontSize = 20.sp, color = whiteBackground)
                }
            }

            Text(
                text = "관심 자격증 목록",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Column() {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(primaryColor)
                        .size(300.dp, 100.dp),
                ) {
                    Text(text = "산업안전기사", fontSize = 20.sp, color = whiteBackground)
                }
            }

        }
//        Text(text = "mypage")
    }
}

@Composable
@Preview
fun contentPreview() {

}
