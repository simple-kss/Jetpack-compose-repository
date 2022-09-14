package com.learnandroid.loginapplication

import android.util.Log
import android.util.Xml
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.input.TextFieldValue
import com.learnandroid.loginapplication.data.JobInfoData
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class ApiManager {


    companion object {
        var TAG = "OLIVER486-API"
        // We don't use namespaces
        private val ns: String? = null
        lateinit var list: List<JobInfoData>

        fun sendApi(certiName: String,
                    state: MutableState<TextFieldValue>,
                    jobState: SnapshotStateList<JobInfoData>
        )  {

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
                            state.value = TextFieldValue("10")
                            list = parse(line) as List<JobInfoData>
                        }
                    }

                    jobState.clear()
                    for (i in 0..list.size - 1) {
                        jobState.add(list.get(i))
                        Log.d(TAG, "Checking: " + jobState.get(i).toString())
                    }

                    // response를 resolve 함 -> 그걸 리스트에 저장.
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

        fun parse(string: String): List<*> {
            Log.d(TAG, "Parsing : " + string)
            // https://stackoverflow.com/questions/64172318/how-to-convert-a-string-to-an-inputstream-in-kotlin
            val inputStream: InputStream = string.byteInputStream()
            inputStream.use { inputStream ->
                val parser: XmlPullParser = Xml.newPullParser()
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
                parser.setInput(inputStream, null)
                parser.nextTag()
                return readFeed(parser)
            }
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

//                        // 참고함
//                        val parser: XmlPullParser = Xml.newPullParser()
//                        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
//                        parser.setInput(inputStream, null)
//                        parser.nextTag()
//                        list = readFeed(parser)
                    }
                }
                Log.d(TAG, "Result" + reponse)
            }
            return reponse
        }

        fun readFeed(parser: XmlPullParser): List<JobInfoData> {
            val entries = mutableListOf<JobInfoData>()

            parser.require(XmlPullParser.START_TAG, ns, "wantedRoot")
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.eventType != XmlPullParser.START_TAG) {
                    continue
                }
                // Starts by looking for the entry tag
                if (parser.name == "wanted") {
                    entries.add(readEntry(parser))
                } else {
                    skip(parser)
                }
            }
            return entries
        }

        private fun readEntry(parser: XmlPullParser) : JobInfoData {
            parser.require(XmlPullParser.START_TAG, ns, "wanted")
            var wantedAuthNo: String? = null
            var company: String? = null
            var title: String? = null
            var salTpNm: String? = null
            var sal: String? = null
            var minSal: String? = null
            var maxSal: String? = null
            var region: String? = null
            var holidayTpNm: String? = null
            var minEdubg: String? = null
            var maxEdubg: String? = null
            var career: String? = null
            var regDt: String? = null
            var closeDt: String? = null
            var infoSvc: String? = null
            var wantedInfoUrl: String? = null
            var wantedMobileInfoUrl: String? = null
            var smodifyDtm: String? = null
            var zipCd: String? = null
            var strtnmCd: String? = null
            var basicAddr: String? = null
            var detailAddr: String? = null
            var empTpCd: String? = null
            var jobsCd: String? = null
            var prefCd: String? = null
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.eventType != XmlPullParser.START_TAG) {
                    continue
                }
                when (parser.name) {
                    "wantedAuthNo" -> wantedAuthNo = readWantedAuthNo(parser)
                    "company" -> company = readCompany(parser)
                    "title" -> title = readTitle(parser)
                    "salTpNm" -> salTpNm = readSalTpNm(parser)
                    "sal" -> sal = readSal(parser)
                    "minSal" -> minSal = readMinSal(parser)
                    "maxSal" -> maxSal = readMaxSal(parser)
                    "region" -> region = readRegion(parser)
                    "holidayTpNm" -> holidayTpNm = readHolidayTpNm(parser)
                    "minEdubg" -> minEdubg = readMinEdubg(parser)
                    "maxEdubg" -> maxEdubg = readMaxEdubg(parser)
                    "career" -> career = readCareer(parser)
                    "regDt" -> regDt = readRegDt(parser)
                    "closeDt" -> closeDt = readCloseDt(parser)
                    "infoSvc" -> infoSvc = readInfoSvc(parser)
                    "wantedInfoUrl" -> wantedInfoUrl = readWantedInfoUrl(parser)
                    "wantedMobileInfoUrl" -> wantedMobileInfoUrl = readWantedMobileInfoUrl(parser)
                    "smodifyDtm" -> smodifyDtm = readSmodifyDtm(parser)
                    "zipCd" -> zipCd = readZipCd(parser)
                    "strtnmCd" -> strtnmCd = readStrtnmCd(parser)
                    "basicAddr" -> basicAddr = readBasicAddr(parser)
                    "detailAddr" -> detailAddr = readDetailAddr(parser)
                    "empTpCd" -> empTpCd = readEmpTpCd(parser)
                    "jobsCd" -> jobsCd = readJobsCd(parser)
                    "prefCd" -> prefCd = readPrefCd(parser)
                    else -> skip(parser)
                }
            }
            return JobInfoData(
                wantedAuthNo, company, title, salTpNm, sal, minSal, maxSal,
                region, holidayTpNm, minEdubg, maxEdubg, career, regDt, closeDt,
                infoSvc, wantedInfoUrl, wantedMobileInfoUrl, smodifyDtm, zipCd,
                strtnmCd, basicAddr, detailAddr, empTpCd, jobsCd, prefCd)
        }

        private fun readWantedAuthNo(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "wantedAuthNo")
            val wantedAuthNo = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "wantedAuthNo")
            return wantedAuthNo
        }

        private fun readCompany(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "company")
            val company = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "company")
            return company
        }

        private fun readTitle(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "title")
            val title = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "title")
            return title
        }

        private fun readSalTpNm(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "salTpNm")
            val salTpNm = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "salTpNm")
            return salTpNm
        }

        private fun readSal(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "sal")
            val sal = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "sal")
            return sal
        }

        private fun readMinSal(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "minSal")
            val minSal = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "minSal")
            return minSal
        }

        private fun readMaxSal(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "maxSal")
            val maxSal = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "maxSal")
            return maxSal
        }

        private fun readRegion(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "region")
            val region = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "region")
            return region
        }

        private fun readHolidayTpNm(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "holidayTpNm")
            val holidayTpNm = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "holidayTpNm")
            return holidayTpNm
        }

        private fun readMinEdubg(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "minEdubg")
            val minEdubg = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "minEdubg")
            return minEdubg
        }

        private fun readMaxEdubg(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "maxEdubg")
            val maxEdubg = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "maxEdubg")
            return maxEdubg
        }

        private fun readCareer(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "career")
            val career = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "career")
            return career
        }

        private fun readRegDt(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "regDt")
            val regDt = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "regDt")
            return regDt
        }

        private fun readCloseDt(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "closeDt")
            val closeDt = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "closeDt")
            return closeDt
        }

        private fun readInfoSvc(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "infoSvc")
            val infoSvc = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "infoSvc")
            return infoSvc
        }

        private fun readWantedInfoUrl(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "wantedInfoUrl")
            val wantedInfoUrl = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "wantedInfoUrl")
            return wantedInfoUrl
        }

        private fun readWantedMobileInfoUrl(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "wantedMobileInfoUrl")
            val wantedMobileInfoUrl = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "wantedMobileInfoUrl")
            return wantedMobileInfoUrl
        }

        private fun readSmodifyDtm(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "smodifyDtm")
            val smodifyDtm = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "smodifyDtm")
            return smodifyDtm
        }

        private fun readZipCd(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "zipCd")
            val zipCd = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "zipCd")
            return zipCd
        }

        private fun readStrtnmCd(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "strtnmCd")
            val strtnmCd = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "strtnmCd")
            return strtnmCd
        }

        private fun readBasicAddr(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "basicAddr")
            val basicAddr = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "basicAddr")
            return basicAddr
        }

        private fun readDetailAddr(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "detailAddr")
            val detailAddr = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "detailAddr")
            return detailAddr
        }

        private fun readEmpTpCd(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "empTpCd")
            val empTpCd = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "empTpCd")
            return empTpCd
        }

        private fun readJobsCd(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "jobsCd")
            val jobsCd = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "jobsCd")
            return jobsCd
        }

        private fun readPrefCd(parser: XmlPullParser): String? {
            parser.require(XmlPullParser.START_TAG, ns, "prefCd")
            val prefCd = readText(parser)
            parser.require(XmlPullParser.END_TAG, ns, "prefCd")
            return prefCd
        }

        private fun readText(parser: XmlPullParser): String {
            var result = ""
            if (parser.next() == XmlPullParser.TEXT) {
                result = parser.text
                parser.nextTag()
            }
            return result
        }
        private fun skip(parser: XmlPullParser) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                throw IllegalStateException()
            }
            var depth = 1
            while (depth != 0) {
                when (parser.next()) {
                    XmlPullParser.END_TAG -> depth--
                    XmlPullParser.START_TAG -> depth++
                }
            }
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