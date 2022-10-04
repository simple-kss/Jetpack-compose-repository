package com.learnandroid.loginapplication.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learnandroid.loginapplication.FirebaseManager
import com.learnandroid.loginapplication.data.ArticleInfo
import com.learnandroid.loginapplication.ui.theme.whiteBackground

@Composable
fun CommunityPage(navController: NavController) {
    Surface() {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .background(whiteBackground),
        ) {
            Text(
                text = "홍길동님 안녕하세요.",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Button(
                onClick = {navController.navigate("article_write_page")}
            ) {
                Text(
                    text = "글작성",
                )
            }
            var articles = FirebaseManager.read_articles()
            LazyColumnWithArticles(articles, navController)
            // 이거로 레이지컬럼 뿌려저야댐
            // https://www.youtube.com/watch?v=V-3sLO_TWl0&ab_channel=HoodLab
        }
    }
}

@Composable
fun LazyColumnWithArticles(articles: SnapshotStateList<ArticleInfo>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Log.d(TAG, "!!!!!!!!  list.size: " + articles.size);
        itemsIndexed(
            articles
//            listOf(100, 200, 300)
        ) { index, item ->
            KotlinWorldCard(order = item, navController)
            Spacer(modifier = Modifier
                .border(width = 1.dp, color = Color.LightGray))
        }
    }

}

@Composable
fun KotlinWorldCard(order: ArticleInfo, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(12.dp)
//             테투리 굵은석
//            .border(width = 4.dp, color = Color.Black)
            .background(whiteBackground)
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Button(
            onClick = {
                navController.navigate("ArticleInfoPage/${order.id}")
            }
        ) {
            Column() {
                Text("${order.id}")
                Text("${order.email}")
                Text("${order.title}")
                Text("${order.contents}")
            }
//            Box(contentAlignment = Alignment.Center)
        }
    }
}

@Composable
@Preview
fun CommunityPagePreview() {
    CommunityPage(rememberNavController())
}