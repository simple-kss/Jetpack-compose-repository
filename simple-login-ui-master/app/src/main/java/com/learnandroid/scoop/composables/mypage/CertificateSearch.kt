package com.learnandroid.scoop.composables

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.learnandroid.scoop.CertiInfoManager
import com.learnandroid.scoop.FirebaseManager
import com.learnandroid.scoop.data.CertificateInfo
import com.learnandroid.scoop.ui.theme.uGray3
import com.learnandroid.scoop.ui.theme.uGray4
import com.learnandroid.scoop.ui.theme.whiteBackground

/*
2022/09/11
search bar참고
https://www.youtube.com/watch?v=O6k5Q2LoL0k&ab_channel=PhilippLackner
https://www.youtube.com/watch?v=D06EV3PngJY&ab_channel=PhilippLackner

 */
var TAG = "oliver486-Certificate"

@Composable
fun CertificateSearch(navController: NavController) {
    CertificateSearchContents()
}

@Composable
fun CertificateSearchContents() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Surface(
        color = whiteBackground,
        modifier = Modifier
            .background(whiteBackground)
            .fillMaxSize()
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "자격증 검색",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(20.dp))
//            Image(
//                painter = painterResource(id = R.drawable.cat_1),
//                contentDescription = "Pokemon",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(CenterHorizontally)
//            )
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                hint = "Search...",
                textState,
            ) {
            }
            // 여기에 검색된 리스트를 뿌려야함.
            Spacer(modifier = Modifier.height(16.dp))
            CertiList(textState)
        }
    }
}

@Composable
fun CertiList(
    state: MutableState<TextFieldValue>
) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 4.dp),
        contentPadding = PaddingValues(10.dp)) {
        var list = CertiInfoManager.getAllList()
        if (state.value != null && !state.value.text.equals("")) {
            list = CertiInfoManager.getListByCertiString(state.value.text)
            itemsIndexed(
                list
            ) { index, item ->
                CertiRow(order = item)
            }
        }
        else {
            itemsIndexed(
                list
            ) { index, item ->
                CertiRow(order = item)
            }
        }
        // 버튼 2개 (취득, 관심) 해야함.
    }
}

@Composable
fun CertiRow(order: CertificateInfo) {
    val user = FirebaseManager.auth?.currentUser
    val name = order.name
    val category = order.category
    // date 관련
    // https://sgkantamani.medium.com/how-to-show-date-picker-in-jetpack-compose-8bc77a3ce408
    var datePicked : String? by remember {
        mutableStateOf(null)
    }
    val activity = LocalContext.current as AppCompatActivity
    // -- date 관련 끝
    val updatedDate = { date : Long? ->
        datePicked = date?.toString()?:""
    }
    Box(
        modifier = Modifier
            .padding(0.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
//                .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                .background(Color.White)
                .fillMaxWidth()
                .height(100.dp)
                .padding(6.dp),
        ) {
            Card(
                elevation = 0.dp,
                modifier = Modifier
                    .width(200.dp)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .background(Color.White)
                ) {
                    Box {
                        Text(
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
                            text = "" + order.name,
                        )
                    }
//                        Box() {
//                            Text("" + order.category)
//                        }
                }
            }
            Button(
                modifier = Modifier
                    .padding(2.dp),
                elevation = null,
                colors = ButtonDefaults.buttonColors(backgroundColor = uGray3),
                onClick = {
                    Log.d(TAG, "acquire called")
                    showDatePicker(activity, name, category, updatedDate)
                }
            ) {
                Text(
                    text = "취득",
                    color = Color.Black
                )
            }
            Button(
                modifier = Modifier
                    .padding(2.dp),
                elevation = null,
                colors = ButtonDefaults.buttonColors(backgroundColor = uGray3),
                onClick = {
                    Log.d(TAG, "interested called")
                    FirebaseManager.write_my_interested(name, category)
                }
            ) {
                Text(
                    text = "관심",
                    color = Color.Black
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .width(400.dp)
                .background(color = uGray4)
        )
    }

}

//@Composable
//fun DatePickerview(
//    datePicked : String?,
//    updatedDate : ( date : Long? ) -> Unit,
//) {
//    val activity = LocalContext.current as AppCompatActivity
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .wrapContentSize(Alignment.TopStart)
//            .padding(top = 10.dp)
//            .border(0.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
//            .clickable{
//                showDatePicker(activity, updatedDate)
//            }
//    ) {
//
//        ConstraintLayout(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//
//            val (lable, iconView) = createRefs()
//
//            Text(
//                text= datePicked?:"Date Picker",
//                color = MaterialTheme.colors.onSurface,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .constrainAs(lable) {
//                        top.linkTo(parent.top)
//                        bottom.linkTo(parent.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(iconView.start)
//                        width = Dimension.fillToConstraints
//                    }
//            )
//
//            Icon(
//                imageVector = Icons.Default.DateRange,
//                contentDescription = null,
//                modifier = Modifier
//                    .size(20.dp, 20.dp)
//                    .constrainAs(iconView) {
//                        end.linkTo(parent.end)
//                        top.linkTo(parent.top)
//                        bottom.linkTo(parent.bottom)
//                    },
//                tint = MaterialTheme.colors.onSurface
//            )
//
//        }
//
//    }
//}

private fun showDatePicker(
    activity : AppCompatActivity,
    name: String,
    category: String,
    updatedDate: (Long?) -> Unit,
) {

    Log.d(TAG, "Before write_my_acquire ")
    val picker = MaterialDatePicker.Builder.datePicker().build()
    picker.show(activity.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        updatedDate(it)
        FirebaseManager.write_my_acquire(name, category, it.toLong())
    }
}

@Composable
@Preview
fun CertificateSearchContentsPreview() {
    CertificateSearchContents()
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    state: MutableState<TextFieldValue>,
    // When we type a charactor or change the tetxt
    onSearch: (String) -> Unit = {
    }
) {
    // 힌트는 디스플레이 되는거임.
    var text by remember {
        mutableStateOf("")
    }
    // true면 엠티스트링 으로 할거임
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    Box(modifier = modifier) {
        // onValueChange는 텍스트 vlaue필드가 변경이되면 트리거되는 거임.
        BasicTextField(
            value = text,
            onValueChange = { value ->
                state.value = TextFieldValue(value)
                text = value
//                onSearch(it) // new string으로 셋팅해준다.
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
//            onImeActionPerformed = { action, softKeyboardController ->
//                if (action == ImeAction.Done) {
////                    viewModel.newSearch(query)
//                    softKeyboardController?.hideSoftwareKeyboard()
//                }
//            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused
                    // if the focus is not active, we enable the hint basically
                    // and else disable

                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}
