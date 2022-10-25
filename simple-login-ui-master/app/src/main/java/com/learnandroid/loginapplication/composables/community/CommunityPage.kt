package com.learnandroid.loginapplication.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learnandroid.loginapplication.FirebaseManager
import com.learnandroid.loginapplication.data.ArticleInfo
import com.learnandroid.loginapplication.ui.theme.uGray2
import com.learnandroid.loginapplication.ui.theme.whiteBackground
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator

@Composable
fun CommunityPage(navController: NavController) {
    Surface() {
        Column(
            verticalArrangement = Arrangement.Top,
            // 내용물 중앙으로 오게함.
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .background(whiteBackground),
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "커뮤니티",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
                Button(
                    elevation = null,
                    onClick = {navController.navigate("article_write_page")},
                    modifier = Modifier
                        .align(Alignment.TopEnd),
                    colors = ButtonDefaults.buttonColors(backgroundColor = whiteBackground)
                ) {
                    Text(
                        text = "글작성",
                        color = uGray2
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "홍길동님 안녕하세요.",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )

            var articles = FirebaseManager.read_articles()

            // 정렬해야함.
//            val comparator: Comparator<ArticleInfo> = compareBy<ArticleInfo> { it.date }

//            Log.d(TAG, "whywhywhywhy!!2asdasd " + articles.size)
//
//            Log.d(TAG, "whywhywhywhy!!1 ")
//            articles.sortedBy { it.date }
//            Log.d(TAG, "whywhywhywhy!!2 " + articles.size)
//
//            for(a in articles) {
//                Log.d(TAG, "item oliver : " + a)
//            }

//            articles.sortBy { it.date } 이거해도안됨

            LazyColumnWithArticles(articles, navController)
            // 이거로 레이지컬럼 뿌려저야댐
            // https://www.youtube.com/watch?v=V-3sLO_TWl0&ab_channel=HoodLab
        }
    }
}

@Composable
fun LazyColumnWithArticles(articles: List<ArticleInfo>, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .background(whiteBackground),
    ) {
        Log.d(TAG, "!!!!!!!!  list.size: " + articles.size);
        itemsIndexed(
            articles
//            listOf(100, 200, 300)
        ) { index, item ->
            KotlinWorldCard(order = item, navController)
//            Spacer(modifier = Modifier
//                .height(1.dp)
//                .fillMaxWidth()
//                .background(color = Color.LightGray)
//            )
//            Spacer(modifier = Modifier
//                .padding(20.dp)
//
//            )

//            Spacer(modifier = Modifier
//                .border(width = 10.dp, color = Color.LightGray))
        }
    }
}

@Composable
fun KotlinWorldCard(order: ArticleInfo, navController: NavController) {
    val df: DateFormat = SimpleDateFormat("yyyy-MM-dd hh-mm-ss")
    Card(
//        elevation = 0.dp,
//        backgroundColor = Color.White,
        modifier = Modifier
            .padding(20.dp)
//             테투리 굵은석
//            .border(width = 4.dp, color = Color.Black)
            .fillMaxWidth()
            .height(100.dp)

    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .background(whiteBackground),
        ) {
            Button(
                elevation = null,
                colors = ButtonDefaults.buttonColors(whiteBackground),
                modifier = Modifier
                    .background(whiteBackground),
                onClick = {
                    navController.navigate("ArticleInfoPage/${order.id}")
                }
            ) {
                ProvideTextStyle(TextStyle(color = Color.Black)) {
                    Column(
                    ) {
//                    Text("${order.id}")
//                    Text("${order.email}")
                        Text("${order.title}",
                            fontWeight = FontWeight.ExtraBold
                        )
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
}

@Composable
@Preview
fun CommunityPagePreview() {
    CommunityPage(rememberNavController())
}