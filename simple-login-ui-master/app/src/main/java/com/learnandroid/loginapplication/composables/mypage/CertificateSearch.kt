package com.learnandroid.loginapplication.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.learnandroid.loginapplication.R
import com.learnandroid.loginapplication.ui.theme.whiteBackground

/*
2022/09/11
search bar참고
https://www.youtube.com/watch?v=O6k5Q2LoL0k&ab_channel=PhilippLackner
https://www.youtube.com/watch?v=D06EV3PngJY&ab_channel=PhilippLackner


 */

@Composable
fun CertificateSearch(navController: NavController) {
    CertificateSearchContents();
}

@Composable
fun CertificateSearchContents() {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.cat_1),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
            }

            // 여기에 검색된 리스트를 뿌려야함.
            Spacer(modifier = Modifier.height(16.dp))
            CertiList();
        }
    }
}

@Composable
fun CertiList(

) {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        // 버튼 2개 (취득, 관심) 해야함.
        


    }

}

@Composable
fun CertiRow(

) {
    Column {
        Row{
            // entry 출력
            //

            Button(onClick = { /*TODO*/ }) {
                Text(
                    text = "취득"
                )
            }

            Button(onClick = { /*TODO*/ }) {
                Text(
                    text = "관심"
                )
            }
        }
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
    // When we type a charactor or change the tetxt
    onSearch: (String) -> Unit = {}
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
            onValueChange = {
                text = it
                onSearch(it) // new string으로 셋팅해준다.
            },
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



