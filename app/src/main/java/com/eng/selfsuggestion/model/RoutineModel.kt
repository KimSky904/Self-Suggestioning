package com.eng.selfsuggestion.model

import java.util.*

data class RoutineModel(
    val content: String?,
    val count: Int = 0,
    val timestamp: Date,
    val docId: String?
)