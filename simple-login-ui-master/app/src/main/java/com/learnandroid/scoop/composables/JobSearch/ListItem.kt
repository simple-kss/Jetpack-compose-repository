package com.learnandroid.scoop.composables.JobSearch

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.learnandroid.scoop.data.JobInfoData
import com.learnandroid.scoop.ui.theme.primaryColor
import com.learnandroid.scoop.ui.theme.whiteBackground
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
                        
                        val infoUrl:String = URLEncoder.encode(data.wantedInfoUrl.toString(),
                            StandardCharsets.UTF_8.toString())
                        val context = LocalContext.current
                        
                        Log.d("oliver486", "oliver486(infoUrl): " + infoUrl)
                        Log.d("oliver486", "oliver486(wantedInfoUrl): " +
                                data.wantedInfoUrl.toString())
                        val uriHandler = LocalUriHandler.current

//                        // https://stackoverflow.com/questions/65567412/jetpack-compose-text-hyperlink-some-section-of-the-text
//                        val annotatedLinkString: AnnotatedString = buildAnnotatedString {
//                            val str = data.wantedInfoUrl.toString()
//                            val startIndex = 0
//                            val endIndex = str.length
////                            val str = "Click this link to go to web site"
////                            val startIndex = str.indexOf("link")
////                            val endIndex = startIndex + 4
//                            append(str)
//                            addStyle(
//                                style = SpanStyle(
//                                    color = Color(0xff64B5F6),
//                                    fontSize = 18.sp,
//                                    textDecoration = TextDecoration.Underline
//                                ), start = startIndex, end = endIndex
//                            )
//                            // attach a string annotation that stores a URL to the text "link"
//                            addStringAnnotation(
//                                tag = "URL",
//                                annotation = data.wantedInfoUrl.toString(),
////                                annotation = "http://github.com",
//                                start = startIndex,
//                                end = endIndex
//                            )
//                        }
//                        // UriHandler parse and opens URI inside AnnotatedString Item in Browse
//
//                        // Clickable text returns position of text that is clicked in onClick callback
//                        Text(text = "웹링크")
//                        ClickableText(
//                            modifier = Modifier
//                                .padding(16.dp)
//                                .fillMaxWidth(),
//                            text = annotatedLinkString,
//                            onClick = {
//                                annotatedLinkString
//                                    .getStringAnnotations("URL", it, it)
//                                    .firstOrNull()?.let { stringAnnotation ->
//                                        uriHandler.openUri(stringAnnotation.item)
//                                    }
//                            }
//                        )

                        val annotatedMobileLinkString: AnnotatedString = buildAnnotatedString {
                            val str = data.wantedMobileInfoUrl.toString()
                            val startIndex = 0
                            val endIndex = str.length
//                            val str = "Click this link to go to web site"
//                            val startIndex = str.indexOf("link")
//                            val endIndex = startIndex + 4
                            append(str)
                            addStyle(
                                style = SpanStyle(
                                    color = Color(0xff64B5F6),
                                    fontSize = 18.sp,
                                    textDecoration = TextDecoration.Underline
                                ), start = startIndex, end = endIndex
                            )
                            // attach a string annotation that stores a URL to the text "link"
                            addStringAnnotation(
                                tag = "URL",
                                annotation = data.wantedMobileInfoUrl.toString(),
                                start = startIndex,
                                end = endIndex
                            )
                        }
                        Text(text = "모바일링크")
                        ClickableText(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            text = annotatedMobileLinkString,
                            onClick = {
                                annotatedMobileLinkString
                                    .getStringAnnotations("URL", it, it)
                                    .firstOrNull()?.let { stringAnnotation ->
                                        uriHandler.openUri(stringAnnotation.item)
                                    }
                            }
                        )

//                        Button(
//                            onClick = {
//                                showWebView = !showWebView
//                            }
//                        ) {
//                            Text(text = "링크", fontWeight = FontWeight.Bold)
//                            Text(text = data.wantedInfoUrl.toString())
//                        }
//                        Button(
//                            onClick = {}
//                        ) {
//                            Text(text = "모바일링크", fontWeight = FontWeight.Bold)
//                            Text(text = data.wantedMobileInfoUrl.toString())
//                        }

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