package com.learnandroid.loginapplication.composables.community

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.learnandroid.loginapplication.FirebaseManager
import com.learnandroid.loginapplication.data.ArticleInfo
import com.learnandroid.loginapplication.data.Comment
import com.learnandroid.loginapplication.ui.theme.primaryColor
import com.learnandroid.loginapplication.ui.theme.uGray1
import com.learnandroid.loginapplication.ui.theme.whiteBackground
import java.text.DateFormat
import java.text.SimpleDateFormat

@Composable
fun ArticleInfoPage(docId: String, navController: NavController) {
    val readArticleById = remember { mutableStateOf(
        ArticleInfo("5", "6", "7", "8", null))
    }
//    val readArticleById:ArticleInfo = remember { mutableStateOf(null) }

    FirebaseManager.read_article_by_id(docId, readArticleById)
    var comment by remember { mutableStateOf("") }

    // https://stackoverflow.com/questions/67252538/jetpack-compose-update-composable-when-list-changes
    var mutComments = remember {
        mutableStateListOf<Comment>()
    }
    mutComments = FirebaseManager.read_comments(docId)

    val scrollState = rememberScrollState()
    Surface() {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .background(whiteBackground)
                .padding(10.dp)
                .verticalScroll(scrollState)
        ) {
            ProvideTextStyle(TextStyle(color = Color.Black)) {
//                Text(
//                    text = id,
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(20.dp))
//                        .fillMaxWidth()
//                        .height(30.dp)
//                        .background(uGray1)
//                )
//                Spacer(modifier = Modifier.height(10.dp))

                if (readArticleById.value != null) {
                    readArticleById.value.email?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(5.dp)
                                .background(uGray1)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    readArticleById.value.title?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(5.dp)
                                .background(uGray1)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    readArticleById.value.contents?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .padding(5.dp)
                                .background(uGray1)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    }
                }
            }
            // 댓글들 쫙 뿌려주기

            Spacer(modifier = Modifier.height(30.dp))
            // 입력하게끔 하기
            BasicTextField(
                value = comment,
                onValueChange = { value ->
                    comment = value
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(uGray1)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    // docs id
                    FirebaseManager.write_comment(docId, comment)
//                    navController.popBackStack()
                          // 새로고침하기
                    mutComments = FirebaseManager.read_comments(docId)
                    navController.navigate("ArticleInfoPage/${docId}")
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
            for (comment in mutComments) {
                CommentCard(order = comment)
            }
        }
    }
}


//@Composable
//fun LazyColumnWithComments(comments: SnapshotStateList<Comment>) {
//    LazyColumn(
//        modifier = Modifier
//            .padding(vertical = 4.dp)
//            .background(whiteBackground),
//    ) {
//        Log.d(TAG, "!!!!!!!!  list.size: " + comments.size);
//        itemsIndexed(
//            comments
//        ) { index, item ->
//            CommentCard(order = item)
//        }
//    }
//}

@Composable
fun CommentCard(order: Comment) {
    val df: DateFormat = SimpleDateFormat("yyyy-MM-dd hh-mm-ss")

    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Button(
            elevation = null,
            colors = ButtonDefaults.buttonColors(whiteBackground),
            modifier = Modifier
                .background(whiteBackground),
            onClick = {}
        ) {
            ProvideTextStyle(TextStyle(color = Color.Black)) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "${order.contents}",
                        maxLines = 2
                    )
                    Text(
                        text = df.format(order.date),
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}
