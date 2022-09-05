package com.learnandroid.loginapplication

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class LoginManager {
    var TAG = "OLIVER486"
    lateinit var firestore : FirebaseFirestore

    constructor() {
        firestore = FirebaseFirestore.getInstance()


    }

    fun write_test() {
        // 데이터 쓰기
        var user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

        firestore.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    public fun write() {
        // 데이터 쓰기
        var memberInfo = hashMapOf(
            "email" to "Ada",
            "id" to "Lovelace",
            "name" to "1815",
            "password" to "1815"
        )
        // 여기서 members는 테이블 이름이 된다.
        firestore.collection("members")
            .add(memberInfo)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
    public fun read_test() {
        // 데이터 읽기
        firestore.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    public fun login(email : String, password : String): Boolean {
        // 어떤 데이터를 읽어 올건지 생각해야함.
        // 데이터 읽기
        firestore.collection("members")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


        if (true) {
            return false
        }
        return true
    }

    public fun register(email : String, id : String, name : String, password : String): Boolean {
        // 데이터 쓰기
        var memberInfo = hashMapOf(
            "email" to email,
            "id" to id,
            "name" to name,
            "password" to password
        )
        // 여기서 members는 테이블 이름이 된다.
        firestore.collection("members")
            .add(memberInfo)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
        return true;
    }

}