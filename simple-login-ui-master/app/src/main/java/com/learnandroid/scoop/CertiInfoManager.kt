package com.learnandroid.scoop

import android.util.Log
import com.learnandroid.scoop.data.CertificateInfo

class CertiInfoManager {

    companion object {
        var TAG = "OLIVER486-CertiInfo"
        var list = mutableListOf<CertificateInfo>()

        fun add(name: String, category: String) {
            var item = CertificateInfo(name, category)
            list.add(item)
        }

        fun printAll() {
            var size = list.size
            for (i: Int in 0..size-1) {
                Log.d(TAG, list.get(i).toString())
            }
        }

        fun getAllList(): MutableList<CertificateInfo> {
            return list
        }

        fun getListByCertiString(searchString: String): MutableList<CertificateInfo> {
            var result = mutableListOf<CertificateInfo>()

            var size = list.size
            for (i: Int in 0..size-1) {
                if (list.get(i).name.contains(searchString)) {
                    result.add(list.get(i))
                }
            }
            return result
        }
    }
}