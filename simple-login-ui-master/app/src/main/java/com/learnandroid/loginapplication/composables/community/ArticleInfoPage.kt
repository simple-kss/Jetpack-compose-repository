package com.learnandroid.loginapplication.composables.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learnandroid.loginapplication.FirebaseManager
import com.learnandroid.loginapplication.data.ArticleInfo
import com.learnandroid.loginapplication.ui.theme.whiteBackground

@Composable
fun ArticleInfoPage(id: String) {
    val readArticleById = remember { mutableStateOf(
        ArticleInfo("5", "6", "7", "8"))
    }
//    val readArticleById:ArticleInfo = remember { mutableStateOf(null) }

    FirebaseManager.read_article_by_id(id, readArticleById)

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
            Text(
                text = id,
                color = Color.Black
            )
            if (readArticleById.value != null) {
                readArticleById.value.email?.let {
                    Text(
                        text = it,
                        color = Color.Black
                    )
                }
                readArticleById.value.title?.let {
                    Text(
                        text = it,
                        color = Color.Black
                    )
                }
                readArticleById.value.contents?.let {
                    Text(
                        text = it,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ArticleInfoPagePreview() {
    ArticleInfoPage("testId")
}
