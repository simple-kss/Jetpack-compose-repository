package com.learnandroid.loginapplication.composables.mypage

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.learnandroid.loginapplication.FirebaseManager
import com.learnandroid.loginapplication.ui.theme.uGray3
import com.learnandroid.loginapplication.ui.theme.uGray4
import com.learnandroid.loginapplication.ui.theme.whiteBackground

@Composable
fun ModifyMyinfo(navController: NavController) {
//    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    val nameValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }
    val nicknameValue = remember { mutableStateOf("") }

    val phoneValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val confirmPasswordValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }
    val confirmPasswordVisibility = remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Surface(
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.70f)
                    .background(whiteBackground)
                    .clip(RoundedCornerShape(30.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "회원정보 수정", fontSize = 30.sp, color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = nicknameValue.value,
                        onValueChange = { nicknameValue.value = it },
                        label = { Text(text = "변경할 닉네임", color = uGray4) },
                        singleLine = true,
                        textStyle = TextStyle(fontWeight = FontWeight.Bold,
                            color = Color.Black),
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = uGray3)
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                            Toast.makeText(context, "닉네임이 \"" + nicknameValue.value
                                    + "\"로 변경되었습니다.",
                                Toast.LENGTH_LONG).show()
                            FirebaseManager.modify_nickname(nicknameValue.value);
                            focusManager.clearFocus()
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp),
                        shape = RoundedCornerShape(50),
                    ) {
                        Text(text = "정보 변경", fontSize = 20.sp, color = Color.White)
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "로그인 하기",
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate("login_page"){
                                launchSingleTop = true
                            }
                        })
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                }
            }
        }
    }
}