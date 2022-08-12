package com.eng.selfsuggestion.model

import com.google.firebase.Timestamp
import com.google.type.Date

data class ArrivedModel(
    val content: String?,
    val timestamp: Timestamp,
    val arriveday: Date,
    val docId: String?

)