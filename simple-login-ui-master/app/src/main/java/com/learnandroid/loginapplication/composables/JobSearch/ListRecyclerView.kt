package com.learnandroid.loginapplication.composables.JobSearch

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

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