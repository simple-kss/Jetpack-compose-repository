//package com.learnandroid.loginapplication.composables.mypage
//
//import android.util.Log
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.AnnotatedString
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//import com.learnandroid.loginapplication.FirebaseManager
//import com.learnandroid.loginapplication.composables.TAG
//import com.learnandroid.loginapplication.data.UserCertificateInfo
//import com.learnandroid.loginapplication.ui.theme.primaryColor
//import com.learnandroid.loginapplication.ui.theme.whiteBackground
//import java.text.DateFormat
//import java.text.SimpleDateFormat
//import java.util.*
//
//class MyPage2 {
//    package com.learnandroid.loginapplication.composables
//
//    import android.util.Log
//    import androidx.compose.foundation.*
//    import androidx.compose.foundation.layout.*
//    import androidx.compose.foundation.lazy.LazyColumn
//    import androidx.compose.foundation.lazy.itemsIndexed
//    import androidx.compose.foundation.shape.RoundedCornerShape
//    import androidx.compose.foundation.text.ClickableText
//    import androidx.compose.material.*
//    import androidx.compose.runtime.*
//    import androidx.compose.runtime.snapshots.SnapshotStateList
//    import androidx.compose.ui.Alignment
//    import androidx.compose.ui.Modifier
//    import androidx.compose.ui.draw.clip
//    import androidx.compose.ui.graphics.Color
//    import androidx.compose.ui.text.AnnotatedString
//    import androidx.compose.ui.text.font.FontWeight
//    import androidx.compose.ui.tooling.preview.Preview
//    import androidx.compose.ui.unit.dp
//    import androidx.compose.ui.unit.sp
//    import androidx.navigation.NavController
//    import com.google.firebase.auth.ktx.auth
//    import com.google.firebase.ktx.Firebase
//    import com.learnandroid.loginapplication.FirebaseManager
//    import com.learnandroid.loginapplication.data.CertificateInfo
//    import com.learnandroid.loginapplication.data.JobInfoData
//    import com.learnandroid.loginapplication.data.UserCertificateInfo
//    import com.learnandroid.loginapplication.ui.theme.primaryColor
//    import com.learnandroid.loginapplication.ui.theme.whiteBackground
//    import java.text.DateFormat
//    import java.text.SimpleDateFormat
//    import java.util.*
//
//    @Composable
//    fun MyPage(navController: NavController) {
//        contentItems(navController)
//    }
//// https://firebase.google.com/docs/auth/web/manage-users#get_a_users_profile
//
//    @Composable
//// navController: NavController
//    fun contentItems(navController: NavController) {
//        var interested = remember { mutableStateListOf<UserCertificateInfo>() }
//        var acquire = remember { mutableStateListOf<UserCertificateInfo>() }
//
//        var user = Firebase.auth.currentUser
//        var displayName: String? = null
//        var email: String? = null
//
//        var visibleInterested by remember { mutableStateOf(true) }
//        var acquireInterested by remember { mutableStateOf(true) }
//
//        user?.let {
//            // Id of the provider (ex: google.com)
//            val providerId = user.providerId
//
//            // UID specific to the provider
//            val uid = user.uid
//
//            // Name, email address, and profile photo Url
//            displayName = user.displayName
//            email = user.email
//            val photoUrl = user.photoUrl
//            Log.d(
//                TAG, "Real DisplayName: " + displayName + ", : " + email +
//                    ", : " + photoUrl)
//        }
//
//        var scrollState = rememberScrollState()
//
//        Surface(
//        ) {
//            Column(
//                verticalArrangement = Arrangement.Center,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(whiteBackground)
//                    .padding(30.dp)
//
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.Start,
//                    verticalArrangement = Arrangement.Top,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight()
//                ) {
//                    Text(
//                        text = displayName + "님 안녕하세요.",
//                        color = Color.Black,
//                        fontSize = 30.sp,
//                        fontWeight = FontWeight.ExtraBold,
//                    )
//                    Column(
//                        Modifier.size(600.dp, 200.dp)
//                    ) {
//                        Button(
//                            modifier = Modifier
//                                .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
//                                .border(
//                                    width = 0.dp,
//                                    color = primaryColor
//                                )
//                                .background(primaryColor)
//                                .fillMaxWidth() // 여기서 300.dp의 width는 씹히게된다.
//                                .size(300.dp, 100.dp),
//                            onClick = {
//                                navController.navigate("modify_myinfo")
//                            },
//                        ) {
//                            Text(
//                                text = "정보 설정",
//                                fontSize = 20.sp,
//                                color = whiteBackground
//                            )
//                        }
//                        Button(
//                            modifier = Modifier
//                                .clip(RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp))
//                                .border(
//                                    width = 0.dp,
//                                    color = primaryColor
//                                )
//                                .background(primaryColor)
//                                .fillMaxWidth()
//                                .size(300.dp, 100.dp),
//                            onClick = {
//                                navController.navigate("certificate_search")
//                            },
//                        ) {
//                            Text(
//                                text = "자격증 검색",
//                                fontSize = 20.sp,
//                                color = whiteBackground
//                            )
//                        }
//                    }
//                    Button(
//                        onClick = { acquireInterested = !acquireInterested },
//                        colors = ButtonDefaults.buttonColors(whiteBackground),
//                        border = BorderStroke(0.dp, whiteBackground),
//                        elevation = null
//                    ) {
//                        Text(
//                            text = AnnotatedString("취득 자격증 목록"),
//                            color = Color.Black,
//                            fontSize = 30.sp,
//                            fontWeight = FontWeight.ExtraBold
//                        )
//                        Text(
//                            text = if (acquireInterested) {
//                                AnnotatedString("    ▲ 접기")
//                            } else {
//                                AnnotatedString("    ▶ 펼치기")
//                            },
//                            color = Color.Gray,
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.ExtraBold
//                        )
//                    }
//                    if (acquireInterested) {
//                        Column {
//                            // 여기서 파이어베이스에서 가져와서 뿌려줘야함.
//                            // 레이지컬럼으로 바로해줘야함.
//                            acquire = FirebaseManager.read_my_acquire()
//                            interestedList(acquire)
////                        interestedList(acquire)
////                        Row(
////                            modifier = Modifier
////                                .clip(RoundedCornerShape(15.dp))
////                                .background(primaryColor)
////                                .fillMaxWidth()
////                                .size(300.dp, 100.dp),
////                        ) {
////                            Text(text = "2012년 2월 18일", fontSize = 20.sp, color = whiteBackground)
////                            Text(text = "정보처리기사", fontSize = 20.sp, color = whiteBackground)
////                        }
//                        }
//                    }
//                    Button(
//                        onClick = { visibleInterested = !visibleInterested },
//                        colors = ButtonDefaults.buttonColors(whiteBackground),
//                        border = BorderStroke(0.dp, whiteBackground),
//                        elevation = null
//                    ) {
//                        Text(
//                            text = AnnotatedString("관심 자격증 목록"),
//                            color = Color.Black,
//                            fontSize = 30.sp,
//                            fontWeight = FontWeight.ExtraBold
//                        )
//                        Text(
//                            text = if (visibleInterested) {
//                                AnnotatedString("    ▲ 접기")
//                            } else {
//                                AnnotatedString("    ▶ 펼치기")
//                            },
//                            color = Color.Gray,
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.ExtraBold
//                        )
//                    }
//                    if (visibleInterested) {
//                        Column {
//                            // 여기서 파이어베이스에서 가져와서 뿌려줘야함.
//                            // 레이지컬럼으로 바로해줘야함.
//                            interested = FirebaseManager.read_my_interested()
//
//                            interestedList(interested)
//                            Row(
//                                modifier = Modifier
//                                    .clip(RoundedCornerShape(15.dp))
//                                    .background(primaryColor)
//                                    .fillMaxWidth()
//                                    .size(300.dp, 100.dp),
//                            ) {
//                                Text(text = "산업안전기사", fontSize = 20.sp, color = whiteBackground)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Composable
//    fun interestedList(list: List<UserCertificateInfo>) {
//        LazyColumn(
//            modifier = Modifier
//                .padding(vertical = 4.dp)
//                .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
//                .fillMaxWidth()
//                .background(primaryColor),
//            contentPadding = PaddingValues(16.dp)
//        ) {
////        var list = FirebaseManager.read_my_interested()
//            Log.d(TAG, "!!!!!!!!  list.size: " + list.size);
//            itemsIndexed(
//                list
////            listOf(100, 200, 300)
//            ) { index, item ->
//                interestedRow(order = item)
//            }
//            // 버튼 2개 (취득, 관심) 해야함.
//        }
//        // val readMyInterested = FirebaseManager.read_my_interested()
//    }
//
//    @Composable
//    fun interestedRow(
//        order: UserCertificateInfo
////    order: Int
//    ) {
//        val name = order.name
//        val category = order.category
//
//        Surface(color = MaterialTheme.colors.primary,
//            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
//        ) {
//            Column(modifier = Modifier
//                .padding(20.dp)
//                .fillMaxWidth()) {
//                Row (
//                    modifier = Modifier
//                        .width(280.dp)
//                        .background(primaryColor),
//                ) {
//                    Text(
//                        fontSize = 18.sp,
//                        color = Color.White,
//                        text = "" + order.name
//                    )
//                    if (order.date != null && order.date != Date(0)) {
//                        val df: DateFormat = SimpleDateFormat("yyyy/MM/dd")
//                        Text(
//                            fontSize = 18.sp,
//                            color = Color.White,
//                            text = " (" + df.format(order.date) + ")"
//                        )
//                    }
////                            Text("" + order)
//                }
//            }
//        }
//    }
//
//
//
//
//}