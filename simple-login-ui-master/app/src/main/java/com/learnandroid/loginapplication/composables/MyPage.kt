package com.learnandroid.loginapplication.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.learnandroid.loginapplication.ui.theme.whiteBackground

@Composable
fun MyPage (navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground),
    ) {
        Column() {
            Text(text = "홍길동님 안녕하세요.")
            Column() {
                Button(
                    onClick = {},
                ) {
                    Text(text = "정보 설정", fontSize = 20.sp)
                }
                Button(
                    onClick = {},
                ) {
                    Text(text = "자격증 검색", fontSize = 20.sp)
                }
            }
            Text(text = "취득 자격증 목록")
            Column() {
                Row() {
                    Text(text = "2012년 2월 18일")
                    Text(text = "정보처리기사")
                }
            }

            Text(text = "관심 자격증 목록")
            Column() {
                Row() {
                    Text(text = "산업안전기사")
                }
            }
            
        }
//        Text(text = "mypage")
    }

//    BackHandler(enabled = true) {
//        Log.d("OLIVER486", "popBackStack");
//        navController.navigate("main_page")
//    }
}

//@Composable
//fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
//
//}
