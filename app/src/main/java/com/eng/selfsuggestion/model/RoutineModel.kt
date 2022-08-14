package com.eng.selfsuggestion.model

import com.google.firebase.Timestamp

data class RoutineModel(
    val content: String?,
    val count: Int = 0,
    val timestamp: Timestamp,
    val docId: String?
)