package com.learnandroid.loginapplication.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.learnandroid.loginapplication.R
import com.learnandroid.loginapplication.ui.theme.primaryColor
import com.learnandroid.loginapplication.ui.theme.whiteBackground

@Composable
fun MainPage(navController: NavController) {
//    val image = imageResource(id = R.drawable.login_image)

    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White), contentAlignment = Alignment.TopCenter
        ) {
            Text(text = "홍길동님 안녕하세요.")
            // Box Column Text Spacer button

            Button(
                onClick = {},
                modifier = Modifier.wrapContentSize()
            ) {
                Text(text = "마이페이지")
            }

            Button(
                onClick = {},
                modifier = Modifier.wrapContentSize()
            ) {
                Text(text = "커뮤니티")
            }

            Button(
                onClick = {},
                modifier = Modifier.wrapContentSize()
            ) {
                Text(text = "구직정보")
            }
        }
    }
}