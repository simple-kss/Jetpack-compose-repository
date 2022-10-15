package com.learnandroid.loginapplication.composables.JobSearch

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.learnandroid.loginapplication.MainViewModel
import com.learnandroid.loginapplication.data.Cat
import com.learnandroid.loginapplication.data.CatsRepo
import com.learnandroid.loginapplication.data.JobInfoData
import com.learnandroid.loginapplication.data.generateRandomCats
import com.learnandroid.loginapplication.ui.theme.primaryColor
import com.learnandroid.loginapplication.ui.theme.whiteBackground
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ListItem(name : String){
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
                    Text(text = "Coursedd")
                    Text(text = name, style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    ))
                }
                OutlinedButton(onClick = { expanded.value = !expanded.value }) {
                    Text(if (expanded.value) "Show less" else "Show more")
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

@Composable
fun JobItem(data: JobInfoData, navController: NavController){
    val expanded = remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded.value) 24.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    var showWebView by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .background(whiteBackground)
            .clip(RoundedCornerShape(30.dp))
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(primaryColor)
            .padding(10.dp)
        ) {
            ProvideTextStyle(TextStyle(color = Color.White)) {
                Row (
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
//                        Text(text = "Course dd")
                        data.title?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight
                                    .ExtraBold),
                                color = Color.White
                            )
                        }
                    }
                    OutlinedButton(
                        onClick = { expanded.value = !expanded.value },
                        modifier = Modifier
                            .background(Color.Black)
                            .clip(RoundedCornerShape(5.dp))
                    ) {
                        Text(
                            color = Color.White,
                            text = if (expanded.value) "Show less" else "Show more")
                    }
                }
                if (expanded.value){
                    Column(
                        modifier = Modifier
                            .padding(bottom = extraPadding.coerceAtLeast(0.dp)
                            )) {
                        // JobData 출력.
                        Text(text = data.company.toString(), fontWeight = FontWeight.Bold)
                        Text(text = data.title.toString(), fontWeight = FontWeight.Bold)

                        Text(text = "지원자격", fontWeight = FontWeight.Bold)
                        Text(text = "경력: " + data.career.toString())
                        Text(text = "학력: " + data.minEdubg.toString() + " ~ " + data.maxEdubg.toString())

                        Text(text = "근무조건", fontWeight = FontWeight.Bold)
                        Text(text = "지역: " + data.basicAddr.toString())
                        Text(text = "급여형태: " + data.salTpNm.toString())
                        Text(text = "월급: " + data.sal.toString())
                        Text(text = "최소월급: " + data.minSal.toString() + "원")
                        Text(text = "최대월급: " + data.maxSal.toString() + "원")
                        Text(text = "근무형태: " + data.holidayTpNm.toString())

                        val infoUrl = URLEncoder.encode(data.wantedInfoUrl.toString(),
                            StandardCharsets.UTF_8.toString())
                        val context = LocalContext.current
                        Button(
                            onClick = {
                                showWebView = !showWebView
//                                context.startActivity(
//                                    Intent(Intent.ACTION_VIEW).also {
//                                        it.data =  Uri.parse(infoUrl)
//                                    }
//                                )
//                                AndroidView(factory = {
//                                    WebView(it).apply {
//                                        layoutParams = ViewGroup.LayoutParams(
//                                            ViewGroup.LayoutParams.MATCH_PARENT,
//                                            ViewGroup.LayoutParams.MATCH_PARENT
//                                        )
//                                        webViewClient = WebViewClient()
//                                        loadUrl(infoUrl)
//                                    }
//                                }, update = {
//                                    it.loadUrl(infoUrl)

//                                if(showWebView) {
//                                    WebPageScreen(infoUrl)
//                                }
                            }
                        ) {
                            Text(text = "링크", fontWeight = FontWeight.Bold)
                            Text(text = data.wantedInfoUrl.toString())
                        }



                        Button(
                            onClick = {}
                        ) {
                            Text(text = "모바일링크", fontWeight = FontWeight.Bold)
                            Text(text = data.wantedMobileInfoUrl.toString())
                        }

//                    val str: String = data.toString()
//                    Text(text = str)

                        // "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
                    }
                }
            }
        }
    }
}



//@Composable
//fun RecyclerView(names : List<String> = List(1000){"$it"}) {
//    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
//        items(items = names){ name ->
//            ListItem(name = name)
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    RecyclerView()
//}

@Preview
@Composable
fun ListPreview() {
//    ListItem("123")
}

//@Composable
//fun WebViewPart(webViewState: WebViewState) {
//    if (webViewState.isLoading) {
//        LinearProgressIndicator(
//            modifier = Modifier.fillMaxWidth(),
//            color = Color.Red
//        )
//    }
//    WebView (
//        state = webViewState,
//        modifier = Modifier.fillMaxWidth(),
//        onCreated = { webView ->
//            // TODO
//        }
//    )
//
//}


@Composable
fun WebPageScreen(infoUrl: String) {
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(infoUrl)
        }
    }, update = {
        it.loadUrl(infoUrl)
    })
}