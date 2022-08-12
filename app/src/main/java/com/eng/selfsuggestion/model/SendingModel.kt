package com.eng.selfsuggestion.model

import com.google.firebase.Timestamp

data class SendingModel (
    val content : String?,
    val timestamp: Timestamp,
    val uid : String,
    val docId : String?
)