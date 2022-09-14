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
import com.learnandroid.loginapplication.composables.JobSearch.ListRecyclerView
import com.learnandroid.loginapplication.ui.theme.LoginApplicationTheme

// 앱이 실행하자마자, 시작하는 프로세스가 MainAcitivity입니다.
// 이 액티비티는 생성할 때, LoginApplication을 만드는데,
// LoginApplication은 startDestination (처음시작화면)으로 login_page로 지정해놓았습니다.
// 그 이유는, 처음 시작할 때, 사용자가 로그인화면부터 보여주어야하기 때문입니다.
class MainActivity : AppCompatActivity() {
    var TAG = "OLIVER486-MainActivity"
    private val mainViewModel: MainViewModel by viewModels()
//    private val mainViewMode1l: CertiInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        var loginManager : LoginManager = LoginManager()
        val open = assets.open("certificateInfo.xml")

        FirebaseManager.init()
        XMLCertiDataManager.init(open)
        CertiInfoManager.printAll()

        //ApiManager.sendApi("정보처리기사", state)

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

        // NavHost는 화면을 어디로 이동할지를 설정하는 키워드
        // navController는 화면 처리기입니다.
        // 미리, 이 navController가 route(문자열로되어있음)에 대응하여 어떤화면을 뿌릴지 정의해놓습니다.
        NavHost(navController = navController, startDestination = "login_page", builder = {
            // composable 명령어로 페이지들을 셋팅합니다.
            composable("login_page", content = { LoginPage(navController = navController) })
            composable("register_page", content = { RegisterPage(navController = navController) })
            composable("main_page", content = { MainPage(navController = navController) })
            composable("job_search_info_page", content = { ListRecyclerView(navController = navController)})
//            composable("job_search_info_page", content = { JobSearchInfo(navController = navController
//                , mainViewModel = mainViewModel) })
            composable("community_page", content = { CommunityPage(navController = navController) })
            composable("mypage_page", content = { MyPage(navController = navController) })
            composable("certificate_search", content = { CertificateSearch(navController = navController)})
        })
    }
}

