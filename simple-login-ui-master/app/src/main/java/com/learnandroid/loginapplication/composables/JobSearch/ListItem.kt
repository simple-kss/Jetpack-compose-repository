package com.learnandroid.loginapplication.composables.JobSearch

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.learnandroid.loginapplication.MainViewModel
import com.learnandroid.loginapplication.data.Cat
import com.learnandroid.loginapplication.data.CatsRepo
import com.learnandroid.loginapplication.data.JobInfoData
import com.learnandroid.loginapplication.data.generateRandomCats

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
                    Text(text = "Course")
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
fun JobItem(data : JobInfoData){
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
                    data.title?.let {
                        Text(text = it, style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.ExtraBold
                        ))
                    }
                }
                OutlinedButton(onClick = { expanded.value = !expanded.value }) {
                    Text(if (expanded.value) "Show less" else "Show more")
                }
            }

            if (expanded.value){
                Column(modifier = Modifier.padding(
                    bottom = extraPadding.coerceAtLeast(0.dp)
                )) {
                    val str: String = data.toString()
                    Text(text = str)
                    // "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
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