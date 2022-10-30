package com.learnandroid.scoop.composables

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.learnandroid.scoop.FirebaseManager
import com.learnandroid.scoop.ui.theme.whiteBackground


@Composable
fun MainPage(navController: NavController) {
    val user = FirebaseManager.auth?.currentUser
    Log.d("OLIVER486-Mainpage", "Login successful " + user?.email)

    val openDialog = remember { mutableStateOf(true) }
    var text by remember { mutableStateOf("") }

    if (user != null) {
        // https://stackoverflow.com/questions/68852110/show-custom-alert-dialog-in-jetpack-compose
        if (user.displayName == null && openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "사용할 이름을 입력해주세요.")
                },
                text = {
                    Column {
                        TextField(
                            value = text,
                            onValueChange = { text = it }
                        )
                        // Text("Custom Text")
                        // 추후에 Checkbox가 필요할 수 도 있으니 우선 주석처리로 남겨놓음.
                        // Checkbox(checked = false, onCheckedChange = {})
                    }
                },
                buttons = {
                    Row(
                        modifier = Modifier.padding(all = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                val user = FirebaseManager.auth?.currentUser

                                val profileUpdates = userProfileChangeRequest {
                                    displayName = text
                                    photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
                                }

                                user!!.updateProfile(profileUpdates)
                                    .addOnCompleteListener { task ->
                                        Log.d(TAG, "addOnCompleteListener. " + user.displayName +
                                                user.displayName.toString())
                                        if (task.isSuccessful) {
                                            Log.d(TAG, "User profile updated.")
                                        }
                                    }
                                openDialog.value = false
                            }
                        ) {
                            Text("Dismiss")
                        }
                    }
                }
            )
        }
        if (user.displayName != null) {
            text = user.displayName.toString()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(30.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight(0.90f),
            ) {
                if (user != null) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.80f)
                            .height(50.dp),
                        text = text + "님 안녕하세요.",
                        color = Color.Black,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                // Box Column Text Spacer button
                Button(
                    onClick = {
                        navController.navigate("mypage_page")
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxWidth(0.80f)
                        .height(100.dp),
                    shape = RoundedCornerShape(25),
                ) {
                    Text(
                        text = "마이페이지",
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        textAlign = TextAlign.Left,
                        fontSize = 30.sp,
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Button(
                    onClick = {
                        navController.navigate("community_page")
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxWidth(0.80f)
                        .height(100.dp),
                    shape = RoundedCornerShape(25),
                ) {
                    Text(
                        text = "커뮤니티",
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        textAlign = TextAlign.Left,
                        fontSize = 30.sp,
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Button(
                    onClick = {
                        navController.navigate("job_search_info_page")
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxWidth(0.80f)
                        .height(100.dp),
                    shape = RoundedCornerShape(25),
                ) {
                    Text(
                        text = "구직정보",
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        textAlign = TextAlign.Left,
                        fontSize = 30.sp,
                    )
                }
            }
        }
    }
}
