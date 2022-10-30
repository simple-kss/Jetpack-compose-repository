package com.learnandroid.scoop.composables.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learnandroid.scoop.FirebaseManager
import com.learnandroid.scoop.ui.theme.*

// 게시글 ID
// 이메일
// title
// 내용
@Composable
fun ArticleWritePage(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var contents by remember { mutableStateOf("") }

    Surface() {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .background(whiteBackground)
                .padding(10.dp),
        ) {
            BasicTextField(
                value = title,
                onValueChange = { value ->
                    title = value
                },
                decorationBox = { innerTextField ->
                    if (title.isEmpty()) {
                        Text("제목을 입력해주세요.", color = uGray2)
                    }
                    innerTextField()  //<-- Add this
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(uGray4)
                    .padding(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))
            BasicTextField(
                value = contents,
                onValueChange = { value ->
                    contents = value
                },
                decorationBox = { innerTextField ->
                    if (contents.isEmpty()) {
                        Text("내용을 입력해주세요.", color = uGray2)
                    }
                    innerTextField()  //<-- Add this
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(uGray4)
                    .padding(10.dp),
            )

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    FirebaseManager.write_artice(title, contents)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(primaryColor)
            ) {
                Text(
                    text = "작성하기",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
@Preview
fun ArticleWritePagePreview() {
    ArticleWritePage(rememberNavController())
}