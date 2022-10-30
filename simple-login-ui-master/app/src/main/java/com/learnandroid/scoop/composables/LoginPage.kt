package com.learnandroid.scoop.composables

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import com.learnandroid.scoop.FirebaseManager
import com.learnandroid.scoop.ui.theme.*

// 처음 앱을 킬때, 등장하게되는 화면입니다.
// 이 화면은 MainActivity에서 기본적으로 실행하게 됩니다.
@Composable
fun LoginPage(navController: NavController) {
    val context = LocalContext.current
    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val findPassword = remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
//        contentAlignment = Alignment.BottomCenter
    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White), contentAlignment = Alignment.TopCenter
//        ) {
//            Image(
//                painterResource(id = R.drawable.login_image),
//                contentDescription = "",
//            )
//        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .clip(RoundedCornerShape(30.dp))
                .background(whiteBackground)
                .padding(10.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "로그인",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),
                    fontSize = 30.sp,
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = emailValue.value,
                        onValueChange = { emailValue.value = it },
                        label = { Text(text = "이메일", color = uGray3) },
//                        placeholder = { Text(text = "Email Address", color = uGray2) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
//                            .clip(RoundedCornerShape(30.dp))
//                            .background(LightLightGray),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = uGray3),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            focusedBorderColor = Green,
//                            unfocusedBorderColor = Yellow)
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    )

                    OutlinedTextField(
                        value = passwordValue.value,
                        onValueChange = { passwordValue.value = it },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
//                                    ImageBitmap = ImageBitmap
//                                        .imageResource(id = R.drawable.password_eye),
                                    contentDescription = "Clear"
                                    // vectorResource deprecated
//                                    imageVector = ImageVector.vectorResource(id = R.drawable.password_eye),
//                                    tint = if (passwordVisibility.value) primaryColor else Color.Gray
                                )
                            }
                        },
                        label = { Text("비밀번호", color = uGray3) },
//                        placeholder = { Text(text = "Password", color = uGray2) },
                        singleLine = true,
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .focusRequester(focusRequester = focusRequester),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            backgroundColor = uGray5,
                            unfocusedBorderColor = uGray3),
//                        onImeActionPerformed = { _, controller ->
//                            controller?.hideSoftwareKeyboard()
//                        }
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        // 내가 추가한 부분
                        onClick = {
                            // auth가 됐다면, main페이지로 가게끔해야한다.
                            FirebaseManager.auth?.signInWithEmailAndPassword(emailValue.value,
                                passwordValue.value)
                                ?.addOnCompleteListener { task ->
                                    Log.d("OLIVER486", "로그인 진입 1")
                                    if(task.isSuccessful) {
                                        Log.d("OLIVER486", "로그인 진입 2")
                                        // Login, 아이디와 패스워드가 맞았을 때
                                        Toast.makeText(context, "로그인 완료",
                                            Toast.LENGTH_LONG).show()
                                        navController.navigate("main_page")
                                    } else {
                                        Log.d("OLIVER486", "로그인 진입 3")
                                        // Show the error message, 아이디와 패스워드가 틀렸을 때
                                        Toast.makeText(context, "로그인 실패. 이유: " +
                                                task.exception?.message,
                                            Toast.LENGTH_LONG).show()
                                    }
                                }
                            Log.d("OLIVER486", "value : " + passwordValue.value)
                            Log.d("OLIVER486", "value : " + emailValue.value)
                        },
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                    ) {
                        Text(text = "로그인", fontSize = 20.sp, color = Color.White)
                    }

                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "계정 생성",
                        color = uGray2,
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate("register_page"){
                            }
                        })
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "비밀번호를 잊으셨나요?",
                        color = uGray3,
                        modifier = Modifier.clickable(onClick = {
                            findPassword.value = true
                        })
                    )
                }
            }
        }
    }

    if (findPassword.value) {
        AlertDialog(
            onDismissRequest = {
                findPassword.value = false
            },
            title = {
                Text(text = "비밀번호를 찾으실 이메일을 입력해주세요.")
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
                            if (text.toString() == null || text.toString() == "") {
                                Toast.makeText(context, "메일을 정확히 입력해 주세요", Toast.LENGTH_LONG).show()
                                
                            } else {
                                FirebaseManager.auth?.sendPasswordResetEmail(text.toString())
                                Toast.makeText(context, "해당 메일로 새로운 비밀번호를 전송했습니다.", Toast.LENGTH_LONG).show()
                            }

//                            val user = FirebaseManager.auth?.currentUser
//
//                            val profileUpdates = userProfileChangeRequest {
//                                displayName = text
//                                photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
//                            }
//
//                            user!!.updateProfile(profileUpdates)
//                                .addOnCompleteListener { task ->
//                                    Log.d(TAG, "addOnCompleteListener. " + user.displayName +
//                                            user.displayName.toString())
//                                    if (task.isSuccessful) {
//                                        Log.d(TAG, "User profile updated.")
//                                    }
//                                }
                            findPassword.value = false
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        )
    }
}

// 안드로이드 코틀린에서의 xml 파싱을 사용하도록한다.
// xml 파일을 불러온다. xml파일은 assets 폴더에 넣어 관리한다.
// assets 폴더에 있는 xml 파일을 읽어온다.
// var inputStream: InputStream = assets.open