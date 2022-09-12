package com.learnandroid.loginapplication

import androidx.lifecycle.ViewModel
import com.learnandroid.loginapplication.data.CertificateInfo


/*
MutableState 뜻: 변경가능한 상태,
즉, 변경되어, composable을 재 구성하려면, 관찰가능한객체를 사용해야하는데
그때 쓰는게 mutableStateOf ..
https://origogi.github.io/android/compose-state/
 */
class CertiInfoViewModel : ViewModel() {

    /*
        var cats by mutableStateOf(CatsRepo.getCats())

    fun addCat(cat: Cat) {
        cats = cats + listOf(cat)
    }

    fun removeCat(cat: Cat) {
        cats = cats.toMutableList().also {
            it.remove(cat)
        }
    }
     */

    var certiInfoList = mutableListOf<CertificateInfo>()
    fun addCertiIntem(name: String, category: String) {
        var info = CertificateInfo(name, category)

        certiInfoList.add(info)
    }



    // 결과를 받을 변수, 초기 결과는 0
    private var total = 0

    // 결과값을 return 하는 함수
    fun getTotal(): Int{
        return total
    }

    // 숫자를 더할 때 사용할 함수
    fun setTotal(input : Int){
        total += input
    }
}