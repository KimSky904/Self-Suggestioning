package com.eng.selfsuggestion.model

import com.google.firebase.Timestamp

data class ArrivedModel(
    val content: String?,
    val timestamp: Timestamp,
    val arriveday: String?,
    val docId: String?

)