package com.learnandroid.loginapplication.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learnandroid.loginapplication.ui.theme.whiteBackground
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.learnandroid.loginapplication.MainViewModel
import com.learnandroid.loginapplication.SearchWidgetState
import com.learnandroid.loginapplication.ui.theme.primaryColor
import com.learnandroid.loginapplication.ui.theme.uGray

// Scaffold는 상단 앱바나 하단 앱바의 슬롯을 제공합니다. 컴포저블의 배치는 내부적으로 처리됩니다.

// scaffold 개념 정리 잘되어있는 곳 :https://mypark.tistory.com/entry/JETPACK-COMPOSE-Scaffold-%EC%A0%95%EB%A6%AC
@Composable
fun JobInfo(mainViewModel: MainViewModel, navController: NavController)  {

}
