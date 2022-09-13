package com.learnandroid.loginapplication

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

/*
https://tre2man.tistory.com/193

 */
class XMLWorkDataManager {
    companion object {
        fun init(inputStream: InputStream) {
            Log.d("OLIVER486-XML", "init")
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

        fun asd(inputStream: InputStream) {
            var factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
            var parser: XmlPullParser = factory.newPullParser()

            parser.setInput(inputStream, null)
            var event = parser.eventType


            while (event != XmlPullParser.END_DOCUMENT) {
                var tag_name = parser.name
                when(event) {
                    XmlPullParser.END_TAG ->{
                        if (tag_name == "certi"){
                            // 파싱한 데이터를 저장한다.
                            // Attribute가 0이면 이름, Attirbute가 1이면 카테고리이다.
                            CertiInfoManager.add(parser.getAttributeValue(0),
                                parser.getAttributeValue(1))
                        }
                    }
                }
                event = parser.next()
            }
        }



//            // 안드로이드 코틀린에서의 xml 파싱을 사용하도록한다.
//            // xml 파일을 불러온다. xml 파일은 assets 폴어에 넣어 관리한다.
//            // assets 폴더에 있는 xml 파일을 읽어온다.
//            var factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
//            var parser: XmlPullParser = factory.newPullParser()
//            // parser에 가져온 xml파일을 넣어 파싱을 한다.
//            parser.setInput(inputStream, null)
//            var event = parser.eventType
//            while (event != XmlPullParser.END_DOCUMENT) {
//                var tag_name = parser.name
//                when(event) {
//                    XmlPullParser.END_TAG ->{
//                        if (tag_name == "certi"){
//                            // 파싱한 데이터를 저장한다.
//                            // Attribute가 0이면 이름, Attirbute가 1이면 카테고리이다.
//                            CertiInfoManager.add(parser.getAttributeValue(0),
//                                parser.getAttributeValue(1))
//                        }
//                    }
//                }
//                event = parser.next()
//            }
//        }
    }
}