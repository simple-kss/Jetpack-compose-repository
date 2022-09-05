package com.learnandroid.loginapplication.composables.JobSearch

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// compose_version = '1.1.0-beta01'
// https://github.com/foxandroid/RecyclerViewJCYTT/blob/master/app/src/main/java/com/example/recyclerviewjcytt/MainActivity.kt
// https://www.youtube.com/watch?v=q7qHRnzcfEQ&ab_channel=Foxandroid
// 출처
@Composable
fun ListRecyclerView(names : List<String> = List(1000) {"$it"}, navController: NavController) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        items(items = names) { name ->
            ListItem(name = name)
        }
    }
}