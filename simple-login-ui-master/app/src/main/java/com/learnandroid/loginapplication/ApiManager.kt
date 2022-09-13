package com.learnandroid.loginapplication

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class ApiManager {

    companion object {
        var TAG = "OLIVER486-API"

        fun sendApi(certiName: String)  {
            Log.d(TAG, "sendApi")
            var temp = certiName.toByteArray(Charsets.UTF_8)
            var result = ""
            for (b in temp) {
                var st = "%"
                st += String.format("%02X", b)
                result += st
            }
            // certiName string을 utf8코드로 변환해야함.
            // API를 가져올 url 등록,
            // 여기에 인증키(authKey)값, 반환타입, 시작페이지(startPage)
            // 최대 표시될 내용물의 수(display) 등이 포함된다. (공식 API사이트에 명시되어잇음)
//        var url = "http://openapi.work.go.kr/opi/opi/opia/wantedApi.do?" +
//                "returnType=xml&startPage=1&display=10&callTp=L&" +
//                "authKey=WNL1POQRW61BKHAAN8XJJ2VR1HK"
//
//        var str1 = "http://openapi.work.go.kr/opi/opi/opia/wantedApi.do?" +
//                "authKey=WNL1POQRW61BKHAAN8XJJ2VR1HK" +
//                "&returnType=xml&startPage=1&display=10&callTp=L&region=11000" +
//                "&keyword==%EA%B0%9C%EB%B0%9C%EC%9E%90"

            var strUrl = "http://openapi.work.go.kr/opi/opi/opia/wantedApi.do?" +
                    "authKey=WNL1POQRW61BKHAAN8XJJ2VR1HK" +
                    "&returnType=xml&startPage=1&display=10&callTp=L&region=11000" +
                    "&keyword==" + result
            val url = URL(strUrl)
            // Debug로그에 출력

            thread {
                var reponse = ""
                // url.openConnection 함수를 통해, 해당
                with(url.openConnection() as HttpURLConnection) {
                    // with 키워드는, "url.openConnection()"의 멤버를
                    // 암묵적으로 사용하는 것을 의미한다.
                    // 즉 아래의 requestMethod는 url.openConnection().requestMethod와 같다.
                    // 요청타입은 GET
                    requestMethod = "GET"  // optional default is GET
                    Log.d(TAG, "openConnection")
                    Log.d(TAG, "Sent 'GET' request to URL :")
                    // 응답 내용을 가져오기 위해, bufferedReader를 통해 가져온다.

                    inputStream.bufferedReader().use {
                        it.lines().forEach { line ->
                            Log.d(TAG, line)
                            // 화면 출력을 담당하는 str에
                            // 응답 결과물 line을 저장.
                            reponse = line
                        }
                    }
                    // 뷰모델에 onResponse를 하면 댄다.
                    Log.d(TAG, "Result" + reponse)
                }
            }

//            GlobalScope.launch {
//                reponse = realSendApi(url)
//                Log.d(TAG, "After call back Real response:" + reponse)
//            }
//            Log.d(TAG, "Fake response:" + reponse)
        }

        suspend fun realSendApi(url: URL): String {
            var reponse = ""
            // url.openConnection 함수를 통해, 해당
            with(url.openConnection() as HttpURLConnection) {
                // with 키워드는, "url.openConnection()"의 멤버를
                // 암묵적으로 사용하는 것을 의미한다.
                // 즉 아래의 requestMethod는 url.openConnection().requestMethod와 같다.
                // 요청타입은 GET
                requestMethod = "GET"  // optional default is GET
                Log.d(TAG, "openConnection")
                Log.d(TAG, "Sent 'GET' request to URL :")
                // 응답 내용을 가져오기 위해, bufferedReader를 통해 가져온다.

                inputStream.bufferedReader().use {
                    it.lines().forEach { line ->
                        Log.d(TAG, line)
                        // 화면 출력을 담당하는 str에
                        // 응답 결과물 line을 저장.
                        reponse = line
                    }
                }
                Log.d(TAG, "Result" + reponse)
            }
            return reponse
        }
    }

    fun send(string: String) {
        // String UTF8로 변환 : https://popcorn16.tistory.com/182
        var str = "안녕하세요 반갑습니다."
        var result = ""
        var temp = str.toByteArray(Charsets.UTF_8)

        Log.d(CertiInfoManager.TAG, "String1: " + temp.toString())
        for (b in temp) {
            var st = "%"
            st += String.format("%02X", b)
            result += st
        }
        Log.d(CertiInfoManager.TAG, "Result: " + result)
    }
}