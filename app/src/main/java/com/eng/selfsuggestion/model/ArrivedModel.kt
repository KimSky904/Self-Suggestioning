package com.eng.selfsuggestion.model

import com.google.firebase.Timestamp
import java.util.*

data class ArrivedModel(
    val content: String?,
    val timestamp: Date,
    val arriveday: String?,
    val docId: String?

)