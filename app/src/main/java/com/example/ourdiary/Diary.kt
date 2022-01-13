package com.example.ourdiary

import com.google.firebase.firestore.DocumentId

data class Diary (
    @DocumentId
    val id: String = "",
    val date: String = "",
    val feeling: String = "",
    val sentence: String = "",
    val title: String = "",
    val weather: String = ""

)