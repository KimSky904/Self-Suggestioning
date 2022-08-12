package com.eng.selfsuggestion.model

data class SendingModel (
    val content : String,
    val timestamp: Long,
    val uid : String,
    val docId : String?
)