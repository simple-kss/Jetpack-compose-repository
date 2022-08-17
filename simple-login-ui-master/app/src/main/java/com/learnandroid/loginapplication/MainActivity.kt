package com.learnandroid.loginapplication

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.learnandroid.loginapplication.composables.*
import com.learnandroid.loginapplication.ui.theme.LoginApplicationTheme

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            LoginApplicationTheme {
                LoginApplication(mainViewModel = mainViewModel)
            }
        }
    }

    @Composable
    fun LoginApplication(mainViewModel: MainViewModel){
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "login_page", builder = {
            composable("login_page", content = { LoginPage(navController = navController) })
            composable("register_page", content = { RegisterPage(navController = navController) })
            composable("main_page", content = { MainPage(
                navController = navController
            ) })

            composable("job_search_info_page", content = { JobSearchInfo(navController = navController
                , mainViewModel = mainViewModel) })
            composable("community_page", content = { CommunityPage(navController = navController) })
            composable("mypage_page", content = { MyPage(navController = navController) })
        })
    }
}
//
//class MyViewmodel : ViewModel() {
//    // state
//    var text by
//
//
//
//    mutableStateOf("")
//    var password by mutableStateOf("")
//
//    // events
//    fun onTextChanged(newString:String) {
//        text = newString
//    }
//
//    fun onPasswordChanged(newString:String) {
//        password = newString
//    }
//
//}


