package com.learnandroid.loginapplication

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.ktx.Firebase
import com.learnandroid.loginapplication.data.ArticleInfo
import com.learnandroid.loginapplication.data.CertificateInfo
import com.learnandroid.loginapplication.data.Comment
import com.learnandroid.loginapplication.data.UserCertificateInfo
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

// Not need it
class FirebaseManager {
//    constructor() {
//        firestore = FirebaseFirestore.getInstance()
//    }

    // 객체를 생성하지 않고 접근 및 함수 호출하기 위해 (자바로 따지면 static 메소드, static value)
    // companion obejct를 만든다.
    companion object {
        var TAG = "OLIVER486-Firebase"
        var auth : FirebaseAuth? = null

        private lateinit var firestore : FirebaseFirestore

        fun init() {
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

        fun write() {
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
        fun read_test() {
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
        fun read_my_interested(): SnapshotStateList<UserCertificateInfo> {
            val userEmail = auth?.currentUser?.email
            var list: SnapshotStateList<UserCertificateInfo> = mutableStateListOf<UserCertificateInfo>()

            Log.d(TAG, "!!!! userEmail: " + userEmail)

            // 데이터 읽기
            firestore.collection("interested")
                .whereEqualTo("member_email", userEmail)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        // 리스트에 다 넣어야 됨
                        // CertificateInfo
                        var name = document.data.get("certificated_name")
                        var category = document.data.get("certificated_category")
                        var date = Date(0)
                        var info:UserCertificateInfo = UserCertificateInfo(
                            name as String,
                            category as String,
                            date
                        )
                        Log.d(TAG, "document name: " + document + ", category: " + category)
                        list.add(info)
                    }
                } // collection end
            return list
        }

        fun read_my_acquire(): SnapshotStateList<UserCertificateInfo> {
            val userEmail = auth?.currentUser?.email
            var list: SnapshotStateList<UserCertificateInfo> = mutableStateListOf<UserCertificateInfo>()

            Log.d(TAG, "!!!! userEmail: " + userEmail)

            // 데이터 읽기
            firestore.collection("acquire")
                .whereEqualTo("member_email", userEmail)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        // 리스트에 다 넣어야 됨
                        // CertificateInfo
                        var name = document.data.get("certificated_name")
                        var category = document.data.get("certificated_category")
                        var date = document.getTimestamp("acquire_date")?.toDate()
                        var info: UserCertificateInfo = UserCertificateInfo(
                            name as String,
                            category as String,
                            date as Date
                        )
                        Log.d(TAG, "document name: " + document + ", category: " + category)
                        list.add(info)
                    }
                } // collection end
            return list
        }

        fun read_articles(): SnapshotStateList<ArticleInfo> {
            val userEmail = auth?.currentUser?.email
            var list: SnapshotStateList<ArticleInfo> = mutableStateListOf<ArticleInfo>()

            // 데이터 읽기
            firestore.collection("articles")
//                .whereEqualTo("member_email", userEmail)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        // 리스트에 다 넣어야 됨
                        // CertificateInfo
                        var id = document.id
                        var member_email = document.data.get("member_email")
                        var member_name = document.data.get("member_name")
                        var title = document.data.get("title")
                        var contents = document.data.get("contents")
                        var date = document.getTimestamp("date")?.toDate()

                        var info : ArticleInfo = ArticleInfo(
                            id,
                            member_email as String,
                            member_name as String,
                            title as String,
                            contents as String,
                            date as Date
                        )
                        Log.d(TAG, "document member_email: " + member_email
                                + ", title: " + title
                                + ", contents: " + contents
                        )
                        list.add(info)
                    }
                    list.sortByDescending { it.date }
                } // collection end


//            list.sortedWith(compareBy({ it.date }))
//            list.sortWith(compareBy<ArticleInfo> { it.date })
//            list.sortByDescending { it.date }
            return list
        }

        fun read_comments(originDocumentId: String): SnapshotStateList<Comment> {
            val userEmail = auth?.currentUser?.email
            var list: SnapshotStateList<Comment> = mutableStateListOf<Comment>()

            // 데이터 읽기
            firestore.collection("comments")
                .whereEqualTo("document_id", originDocumentId)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        // 리스트에 다 넣어야 됨
                        // CertificateInfo
                        var documentId = document.data.get("document_id")
                        var member_email = document.data.get("member_email")
                        var member_name = document.data.get("member_name")
                        var contents = document.data.get("contents")
                        var date = document.getTimestamp("date")?.toDate()

                        var comment : Comment = Comment(
                            documentId as String,
                            member_email as String,
                            member_name as String,
                            contents as String,
                            date as Date
                        )
                        Log.d(TAG, "document member_email: " + member_email
                                + ", contents: " + contents
                        )
                        list.add(comment)
                    }
                } // collection end
//            list.sortedWith(compareBy({ it.date }))
//            list.sortWith(compareBy<ArticleInfo> { it.date })
            return list
        }

        fun write_my_interested(certificateName: String, category: String) {
            val userEmail = auth?.currentUser?.email
            var duplicated = false
            if (certificateName == null) {
                Log.e(TAG, "certificateName error")
            }
            if (category == null) {
                Log.e(TAG, "category error")
            }
            Log.d(TAG, "write_my_interested called")

            // 중복검사
            // https://stackoverflow.com/questions/51054114/`firebase`-cloud-firestore-query-whereequalto-for-reference
            firestore.collection("interested")
                .whereEqualTo("member_email", userEmail)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "중복검사 ${document.id} => ${document.data} 추가정보: "
                        + document.data.get("certificated_name") + ", " + certificateName)

                        if (certificateName.equals(document.data.get("certificated_name"))) {
                            duplicated = true
                            Log.d(TAG, "이미 들어가있는 값입니다." + duplicated)
                        }
                        else {
                            Log.d(TAG, "새로운 값 입니다.")
                        }
                    }

                    if (!duplicated) {
                        Log.d(TAG, "right result " + duplicated)
                        // 데이터 쓰기
                        var interestedInfo = hashMapOf(
                            "member_email" to userEmail,
                            "certificated_name" to certificateName,
                            "certificated_category" to category,
                        )

                        // 여기서 interested 테이블 이름
                        firestore.collection("interested")
                            .add(interestedInfo)
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }
                }
            } // collection end
        }

        fun delete_my_interested(
            certificateName: String,
            list: SnapshotStateList<UserCertificateInfo>,
            deleteCheck: MutableState<Boolean>,

            ) {
            val userEmail = auth?.currentUser?.email
            var found = false
            var id: String? = null

            Log.d(TAG, "delete_my_interested called" + certificateName)
            if (certificateName == null) {
                Log.e(TAG, "certificateName error")
            }
            Log.d(TAG, "write_my_interested called")

            // 중복검사
            // https://stackoverflow.com/questions/51054114/`firebase`-cloud-firestore-query-whereequalto-for-reference
            firestore.collection("interested")
                .whereEqualTo("member_email", userEmail)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "중복검사 ${document.id} => ${document.data} 추가정보: "
                                + document.data.get("certificated_name") + ", " + certificateName)

                        if (certificateName.equals(document.data.get("certificated_name"))) {
                            found = true
                            id = document.id
                            Log.d(TAG, "이미 들어가있는 값입니다. (값 찾음)" + found)
                        }
                        else {
                            Log.d(TAG, "새로운 값 입니다.")
                        }
                    }

                    if (found) {
                        // 데이터 삭제하기
                        var interestedInfo = hashMapOf(
                            "member_email" to userEmail,
                            "certificated_name" to certificateName,

                        )

                        id?.let {
                            firestore.collection("interested")
                                .document(it)
                                .delete()
                                .addOnSuccessListener {
                                    Log.d(TAG, "정상적으로 삭제 완료")
                                    deleteCheck.value = true
//                                    list = read_my_interested()
                                }
                        }

                        // 여기서 interested 테이블 이름
//                        firestore.collection("interested")
//                            .add(interestedInfo)
//                            .addOnSuccessListener { documentReference ->
//                                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//                            }
//                            .addOnFailureListener { e ->
//                                Log.w(TAG, "Error adding document", e)
//                            }
                    }
                } // collection end
        }

        fun write_my_acquire(
            certificateName: String,
            category: String,
            updatedDate: Long
        ) {
            val userEmail = auth?.currentUser?.email
            var duplicated = false

            var datePicked : String? = null
//            var acquireDate = updatedDate
//            acquireDate = { date : Long? ->
//                datePicked = DateFormater(date)
//                Log.e(TAG, "oliver486 write_my_acquire (date)" + date)
//            }
//            var currentDate = Date()
            var currentDate = Date(updatedDate)

//            Log.e(TAG, "oliver486 write_my_acquire  " + updatedDate + ", " + currentDate)
//            Log.e(TAG, "oliver486 write_my_acquire(DateFormater)  " + DateFormater(updatedDate))
//            Log.e(TAG, "oliver486 write_my_acquire(currentDate2)  " + currentDate)

            if (certificateName == null) {
                Log.e(TAG, "acquire error")
            }
            if (category == null) {
                Log.e(TAG, "acquire error")
            }
            Log.d(TAG, "write_my_acquire called")

            // 중복검사
            // https://stackoverflow.com/questions/51054114/firebase-cloud-firestore-query-whereequalto-for-reference
            firestore.collection("acquire")
                .whereEqualTo("member_email", userEmail)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "중복검사 ${document.id} => ${document.data} 추가정보: "
                                + document.data.get("certificated_name") + ", " + certificateName)

                        if (certificateName.equals(document.data.get("certificated_name"))) {
                            duplicated = true
                            Log.d(TAG, "이미 들어가 있는 값입니다." + duplicated)
                        }
                        else {
                            Log.d(TAG, "새로운 값 입니다.")
                        }
                    }
                    if (!duplicated) {
                        Log.d(TAG, "right result " + duplicated)
                        // 데이터 쓰기
                        var acquireInfo = hashMapOf(
                            "member_email" to userEmail,
                            "certificated_name" to certificateName,
                            "certificated_category" to category,
                            "acquire_date" to currentDate,
                        )
                        // 여기서 acquire 테이블 이름
                        firestore.collection("acquire")
                            .add(acquireInfo)
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }
                    }
                } // collection end
        }

        // output: 12/10/2022
        fun DateFormater(milliseconds : Long?) : String?{
            milliseconds?.let{
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                val calendar: Calendar = Calendar.getInstance()
                calendar.setTimeInMillis(it)
                return formatter.format(calendar.getTime())
            }
            return null
        }

        fun DateFormater_all_time(milliseconds : Long?) : String?{
            milliseconds?.let{
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                val calendar: Calendar = Calendar.getInstance()
                calendar.setTimeInMillis(it)
                return formatter.format(calendar.getTime())
            }
            return null
        }

        fun write_artice(title: String, contents: String) {
            var currentDate = Date()
//            var df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
//            var currentDataWithFormat = df.format(currentDate)

            val userEmail = auth?.currentUser?.email
            val name = auth?.currentUser?.displayName
            if (title == null) {
                Log.e(TAG, "acquire error")
            }
            if (contents == null) {
                Log.e(TAG, "acquire error")
            }
            Log.d(TAG, "write_article called")
            // 데이터 쓰기
            var articleInfo = hashMapOf(
                "member_email" to userEmail,
                "member_name" to name,
                "title" to title,
                "contents" to contents,
                "date" to currentDate,
            )
            firestore.collection("articles")
                .add(articleInfo)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }

        fun write_comment(documentId: String, contents: String)
        {
            var currentDate = Date()

            val userEmail = auth?.currentUser?.email
            val name = auth?.currentUser?.displayName
            if (contents == null) {
                Log.e(TAG, "contents error")
                return
            }
            if (documentId == null) {
                Log.e(TAG, "documentId error")
                return
            }

            Log.d(TAG, "write_comment called")
            // 데이터 쓰기
            var commentInfo = hashMapOf(
                "member_email" to userEmail,
                "member_name" to name,
                "document_id" to documentId,
                "contents" to contents,
                "date" to currentDate,
            )
            firestore.collection("comments")
                .add(commentInfo)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }

        fun modify_nickname(value: String) {
            val user = FirebaseManager.auth?.currentUser
            val profileUpdates = userProfileChangeRequest {
                displayName = value
            }
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    Log.d(TAG, "addOnCompleteListener. " + user.displayName +
                            user.displayName.toString())
                    if (task.isSuccessful) {
                        Log.d(TAG, "User profile updated.")
                    }
                }
        }

        fun login(email : String, password : String): Boolean {
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

        fun register(email : String, name : String, hp : String,
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

            return true
        }

        fun read_article_by_id(id: String, readArticleById: MutableState<ArticleInfo>) {

            // 데이터 읽기
            // https://stackoverflow.com/questions/47876754/query-firestore-database-for-document-id
            firestore.collection("articles")
                .whereEqualTo(FieldPath.documentId(),  id)
//                .whereEqualTo("member_email", userEmail)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        // 리스트에 다 넣어야 됨
                        // CertificateInfo
                        var id = document.id
                        var member_email = document.data.get("member_email")
                        var member_name = document.data.get("member_name")
                        var title = document.data.get("title")
                        var contents = document.data.get("contents")
                        var date = document.getTimestamp("date")?.toDate()

                        readArticleById.value = ArticleInfo(
                            id,
                            member_email as String,
                            member_name as String,
                            title as String,
                            contents as String,
                            date as Date
                        )
                        Log.d(TAG, "read_article_by_id" +
                                " document member_email: " + member_email
                                + ", title: " + title
                                + ", contents: " + contents
                        )
                    }
                } // collection end
        }

        fun register_legacy(email : String, name : String, hp : String,
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
            return true
        }
    }
}

fun DateFormater(milliseconds : Long?) : String?{
    milliseconds?.let{
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(it)
        return formatter.format(calendar.getTime())
    }
    return null
}