package com.learnandroid.scoop.composables

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learnandroid.scoop.FirebaseManager
import com.learnandroid.scoop.ui.theme.*

@Composable
fun RegisterPage(navController: NavController) {
    val context = LocalContext.current
    val nameValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }
    val phoneValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val confirmPasswordValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }
    val confirmPasswordVisibility = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
//        contentAlignment = Alignment.BottomCenter
    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White),
//            contentAlignment = Alignment.TopCenter
//        ) {
//            Image(
//                painterResource(id = R.drawable.register_page),
//                contentDescription = "",
//            )
//        }
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
                    text = "회원가입", fontSize = 30.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Name은 로그인 이후에 입력받게 한다.
//                    OutlinedTextField(
//                        value = nameValue.value,
//                        onValueChange = { nameValue.value = it },
//                        label = { Text(text = "Name") },
//                        placeholder = { Text(text = "Name") },
//                        singleLine = true,
//                        modifier = Modifier
//                            .fillMaxWidth(0.8f)
//                            .border(
//                                width = 1.dp,
//                                color = uGray
//                            )
//                    )
                    OutlinedTextField(
                        value = emailValue.value,
                        onValueChange = { emailValue.value = it },
                        label = { Text(text = "이메일", color = uGray4) },
//                        placeholder = { Text(text = "Email Address") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
//                            .border(
//                                width = 1.dp,
//                                color = uGray
//                            ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = uGray3)
                    )
// 추후 업데이트 예정
//                    OutlinedTextField(
//                        value = phoneValue.value,
//                        onValueChange = { phoneValue.value = it },
//                        label = { Text(text = "Phone Number") },
//                        placeholder = { Text(text = "Phone Number") },
//                        singleLine = true,
//                        modifier = Modifier
//                            .fillMaxWidth(0.8f)
//                            .border(
//                                width = 1.dp,
//                                color = uGray
//                            ),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//                    )

                    OutlinedTextField(
                        value = passwordValue.value,
                        onValueChange = { passwordValue.value = it },
                        label = { Text(text = "비밀번호", color = uGray4) },
//                        placeholder = { Text(text = "Password") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = "Clear"
//                                    imageVector = ImageVector.vectorResource(id = R.drawable.password_eye),
//                                    tint = if (passwordVisibility.value) primaryColor else Color.Gray
                                )
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = uGray3),
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation()
                    )

                    OutlinedTextField(
                        value = confirmPasswordValue.value,
                        onValueChange = { confirmPasswordValue.value = it },
                        label = { Text(text = "비밀번호 확인", color = uGray4) },
//                        placeholder = { Text(text = "Confirm Password") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        trailingIcon = {
                            IconButton(onClick = {
                                confirmPasswordVisibility.value = !confirmPasswordVisibility.value
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = "Clear"
//                                    imageVector = ImageVector.vectorResource(id = R.drawable.password_eye),
//                                    tint = if (confirmPasswordVisibility.value) primaryColor else Color.Gray
                                )
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = uGray3),
                        visualTransformation = if (confirmPasswordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                            if (confirmPasswordValue.value != passwordValue.value) {
                                Toast.makeText(
                                    context, "비밀번호와 비밀번호 확인이 불일치합니다. 다시 한번 확인해주세요.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else {
                                Toast.makeText(
                                    context, "회원가입이 완료 되었습니다.",
                                    Toast.LENGTH_LONG
                                ).show()
                                // TODO: confirm 비밀번호 틀렸을 때 처리 나중에 하기

                                // 새로 계정 만들기
                                FirebaseManager.auth?.createUserWithEmailAndPassword(
                                    emailValue.value,
                                    confirmPasswordValue.value
                                )?.addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            // Login, 아이디와 패스워드가 맞았을 때
                                            Toast.makeText(
                                                context, "계정 생성 완료",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        } else {
                                            // Show the error message, 아이디와 패스워드가 틀렸을 때
                                            Toast.makeText(
                                                context, task.exception?.message,
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                navController.popBackStack()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp),
                        shape = RoundedCornerShape(50),
                    ) {
                        Text(text = "회원가입 신청", fontSize = 20.sp, color = Color.White)
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

@Composable
@Preview
fun RegisterPagePreview() {
    RegisterPage(rememberNavController())
}