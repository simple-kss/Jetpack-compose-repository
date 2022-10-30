package com.learnandroid.scoop.composables.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.learnandroid.scoop.FirebaseManager
import com.learnandroid.scoop.data.ArticleInfo
import com.learnandroid.scoop.data.Comment
import com.learnandroid.scoop.ui.theme.*
import java.text.DateFormat
import java.text.SimpleDateFormat

@Composable
fun ArticleInfoPage(docId: String, navController: NavController) {
    val readArticleById = remember { mutableStateOf(
        ArticleInfo("5", "6", "7", "8", "1", null))
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
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                Modifier.fillMaxWidth()
            ) {
                readArticleById.value.title?.let {
                    Text(
                        text = it,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                    )
                }
//                Button(
//                    elevation = null,
//                    onClick = {navController.navigate("article_write_page")},
//                    modifier = Modifier
//                        .align(Alignment.TopEnd),
//                    colors = ButtonDefaults.buttonColors(backgroundColor = whiteBackground)
//                ) {
//                    Text(
//                        text = "글작성",
//                        color = uGray2
//                    )
//                }
            }
            Spacer(modifier = Modifier.height(20.dp))


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
                    readArticleById.value.name?.let {
                        Text(
                            text = "작성자: " + it,
                            modifier = Modifier
                                .clip(RoundedCornerShape(30.dp))
                                .background(whiteBackground)
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(10.dp)
                        )
                    }
//                    readArticleById.value.email?.let {
//                        Text(
//                            text = it,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(50.dp)
//                                .padding(5.dp)
//                                .background(uGray1)
//                                .clip(RoundedCornerShape(20.dp))
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(10.dp))
//                    readArticleById.value.title?.let {
//                        Text(
//                            text = it,
//                            modifier = Modifier
////                                .padding(5.dp)
//                                .clip(RoundedCornerShape(30.dp))
//                                .fillMaxWidth()
//                                .height(50.dp)
//                                .background(uGray4)
//
//                        )
//                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    readArticleById.value.contents?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .fillMaxWidth()
                                .height(300.dp)
                                .background(uGray4)
                                .padding(10.dp)
                        )
                    }
                }
            }
            // 댓글들 쫙 뿌려주기

            Spacer(modifier = Modifier.height(30.dp))
            // 입력하게끔 하기

            // 아래의 provi
            ProvideTextStyle(TextStyle(color = Color.Black)) {
                TextField(
                    value = comment,
                    onValueChange = { value ->
                        comment = value
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(uGray5)
                        .padding(10.dp),
                    placeholder = { Text(text = "댓글을 입력하세요.", color = uGray3)}
                )
            }

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
    Spacer(modifier = Modifier.height(10.dp))
//    Card(
//        modifier = Modifier
//            .background(uGray4)
////            .padding(20.dp)
////            .fillMaxWidth()
//            .height(50.dp)
//    ) {

        Column (
//            elevation = null,
//            colors = ButtonDefaults.buttonColors(uGray4),
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .fillMaxWidth()
                .height(100.dp)
                .background(uGray4)
                .padding(10.dp),
//            onClick = {}
        ) {
            ProvideTextStyle(TextStyle(color = Color.Black)) {
//                Column(
//                    horizontalAlignment = Alignment.
//                ) {
                    Text(
                        text = "작성자: ${order.name}",
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                    Text(
                        text = "${order.contents}",
                        textAlign = TextAlign.Left,
                        maxLines = 1
                    )
//                    Text(
//                        text = df.format(order.date),
//                        color = Color.LightGray
//                    )
//                }
            }
        }
//    }
    Spacer(modifier = Modifier.height(10.dp))
}
