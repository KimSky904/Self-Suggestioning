package com.eng.selfsuggestion.model

import com.google.firebase.Timestamp
import java.util.*

data class SendingModel (
    val content : String?,
    val timestamp: Date,
    val uid : String,
    val docId : String?
)