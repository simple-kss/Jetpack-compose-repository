package com.learnandroid.loginapplication

import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginManager {
//    constructor() {
//        firestore = FirebaseFirestore.getInstance()
//    }

    // 객체를 생성하지 않고 접근 및 함수 호출하기 위해 (자바로 따지면 static 메소드, static value)
    // companion obejct를 만든다.
    companion object {
        var TAG = "OLIVER486"
        public var auth : FirebaseAuth? = null

        private lateinit var firestore : FirebaseFirestore


        public fun init() {
            firestore = FirebaseFirestore.getInstance()
            auth = Firebase.auth
        }

        fun write_test() {
            // 데이터 쓰기
            var user = hashMapOf(
                "first" to "2022_09_07_1220",
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

            // 해당데이터가 있는 지 확인해야함.



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

        public fun register(email : String, name : String, hp : String,
                            password : String): Boolean {
            if (email.isNotEmpty() && name.isNotEmpty() && hp.isNotEmpty()
                && password.isNotEmpty()) {

//                auth?.createUserWithEmailAndPassword(email, password)
//                    ?.addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            Toast.makeText(
//                                this, "계정 생성 완료.",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            finish() // 가입창 종료
//                        } else {
//                            Toast.makeText(
//                                this, "계정 생성 실패",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
            }

            return true;
        }

        public fun register_legacy(email : String, name : String, hp : String,
                            password : String): Boolean {
            // 일시적으로, 회원 레코드의 고유레코드를 지칭하는 id는
            // email + name으로 만든다.
            var id = email + name
            // 데이터 쓰기
            var memberInfo = hashMapOf(
                "email" to email,
                "id" to id,
                "name" to name,
                "hp" to hp,
                "password" to password
            )
            // 여기서 members는 테이블 이름이 된다.
            firestore.collection("members")
                .add(memberInfo)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, " DocumentSnapshot added with ID: ${documentReference.id}")
                    Log.d(TAG, " Data email: ${email}")
                    Log.d(TAG, " Data id: ${id}")
                    Log.d(TAG, " Data name: ${name}")
                    Log.d(TAG, " Data hp: ${hp}")
                    Log.d(TAG, " Data password: ${password}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
            return true;
        }
    }
}