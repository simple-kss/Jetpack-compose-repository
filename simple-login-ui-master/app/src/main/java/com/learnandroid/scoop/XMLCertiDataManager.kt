package com.learnandroid.scoop

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

/*
2022/09/11
출처: https://m.blog.naver.com/oklmg/222113565643

 */

class XMLCertiDataManager {

    companion object {
        fun init(inputStream: InputStream) {
            Log.d("OLIVER486-XML", "init")
            // 안드로이드 코틀린에서의 xml 파싱을 사용하도록한다.
            // xml 파일을 불러온다. xml 파일은 assets 폴어에 넣어 관리한다.
            // assets 폴더에 있는 xml 파일을 읽어온다.
            var factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
            var parser: XmlPullParser = factory.newPullParser()
            // parser에 가져온 xml파일을 넣어 파싱을 한다.
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
    }
}