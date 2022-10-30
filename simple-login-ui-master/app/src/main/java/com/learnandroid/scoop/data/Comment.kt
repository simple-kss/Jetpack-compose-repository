package com.learnandroid.scoop.data

import java.util.*

data class Comment(
    var documentId: String?,
    var email: String?,
    var name: String?,
    var contents: String?,
    var date: Date?
)
