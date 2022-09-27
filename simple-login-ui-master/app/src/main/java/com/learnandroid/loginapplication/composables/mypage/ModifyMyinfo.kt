package com.learnandroid.loginapplication.composables.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.learnandroid.loginapplication.R
import com.learnandroid.loginapplication.composables.CertiList
import com.learnandroid.loginapplication.composables.SearchBar

@Composable
fun ModifyMyinfo(navController: NavController) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(text = "hello world")
        }
    }
}