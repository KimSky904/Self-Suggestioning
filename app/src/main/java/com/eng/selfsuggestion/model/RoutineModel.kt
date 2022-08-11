package com.eng.selfsuggestion.model

import kotlin.collections.ArrayList

data class RoutineModel (
    val content : String,
    val count : Int = 0,
    val keyword : ArrayList<String>,
    val timestamp: Long,
    val uid : String
)