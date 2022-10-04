package com.learnandroid.loginapplication.composables.JobSearch

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.learnandroid.loginapplication.ApiManager
import com.learnandroid.loginapplication.composables.TAG
import com.learnandroid.loginapplication.data.JobInfoData
import com.learnandroid.loginapplication.ui.theme.whiteBackground

// compose_version = '1.1.0-beta01'
// https://github.com/foxandroid/RecyclerViewJCYTT/blob/master/app/src/main/java/com/example/recyclerviewjcytt/MainActivity.kt
// https://www.youtube.com/watch?v=q7qHRnzcfEQ&ab_channel=Foxandroid
// 출처
@Composable
fun JobSearchInfo(navController: NavController) {
    // 1. saerch 바 후에
    // 2. api 날리기
    // 3. 뿌려주기

    //// -> 날리고 받은걸 확인 후 뿌려야된다.
    //// ->그런데 API가 비동기로 되어있어서 API send이후 바로 결과를 보여주지 못한다.
    //// -<
    // 뿌려주기
    var textState = remember { mutableStateOf(TextFieldValue("")) }
    var jobState = remember { mutableStateListOf<JobInfoData>()}

    Surface (
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(whiteBackground)
        ) {
            SearchJobBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                hint = "Search...",
                textState,
                jobState
            ) {
            }
            // 여기에 검색된 리스트를 뿌려야함.
            Spacer(modifier = Modifier.height(16.dp))

            JobInfoList(
                textState,
                jobState,
                navController
            )
        }
//
//    var names : List<String> = List(1000) {"$it"}
//
//    LazyColumn(
//        modifier = Modifier.padding(vertical = 4.dp)
//    ) {
//        items(items = names) { name ->
//            ListItem(name = name)
//            // JobInfoRow2
//        }
//    }
    }
}

@Composable
fun JobInfoList(
    state: MutableState<TextFieldValue>,
    jobState: SnapshotStateList<JobInfoData>,
    navController: NavController
) {
//    state.value.text
//    var listSize: Int
    if (state.value.text == null || state.value.text.equals("")) {
//        listSize = 0
    } else {
//        listSize = 10
        // state.value.text.toInt()
    }

//    var names : List<String> = List(listSize) {"$it"}
    var names : List<JobInfoData> = jobState

    LazyColumn(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .background(whiteBackground)
    ) {
//        items(items = names) { name ->
//            ListItem(name = name)
//        }

        items(items = names) { data ->
            JobItem(data = data, navController)
        }
    }

}

@Composable
fun JobInfoList2() {
    var names : List<String> = List(1000) {"$it"}

    LazyColumn(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        items(items = names) { name ->
            ListItem(name = name)
            // JobInfoRow2
        }
    }
}

@Composable
fun SearchJobBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    state: MutableState<TextFieldValue>,
    // When we type a charactor or change the tetxt
    jobState: SnapshotStateList<JobInfoData>,
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
                // 이부분을 키보드 search로 클릭했을 때로 해야됨.
                // 하지만 JobInfo에선 필요없음
                // 검색 버튼 눌렀을때만, 업데이트 되게 해야됨.
                text = value
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions (
                onSearch = {
                    Log.d(TAG, "Search Click")
                },
                onDone = {
                    Log.d(TAG, "Done Click")
                    // 검색 버튼 눌렀을때만, 업데이트 되게 해야됨.
                    state.value = TextFieldValue(text)

                    // 이때 해당 value를 api로 쏴야함
                    // poc 체크.. 해서 callback 받아서, state.value가 바뀌어지면
                    // 해당 값이 바뀌어지는진
                    ApiManager.sendApi(text, state, jobState)
                }
            ),
            // 클릭했을 시 API를 쏴버리기. -> 그걸 state에 저장시켜야됨 string이든 뭐로든.
//            KeyboardActions = KeyboardActions(
//                onSearch = {
//                    Log.d(TAG, "Search Click")
//                }
//            ),
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


@Composable
fun JobInfoRow2(name : String) {
    val expanded = remember { mutableStateOf(false)}
    val extraPadding by animateDpAsState(
        if (expanded.value) 24.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)){

        Column(modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()) {
            Row{
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(text = "Course")
                    Text(text = name, style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    ))
                }
                OutlinedButton(
                    modifier = Modifier
                        .background(Color.Black),
                    onClick = { expanded.value = !expanded.value }
                ) {
                    Text(
                        color = Color.White,
                        text = if (expanded.value) "Show less" else "Show more")
                }
            }

            if (expanded.value){
                Column(modifier = Modifier.padding(
                    bottom = extraPadding.coerceAtLeast(0.dp)
                )) {
                    Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
                }
            }
        }
    }
}

