package com.eng.selfsuggestion.model

import java.util.*

data class ArrivedModel (
    val content : String,
    val timestamp: Long,
    val arriveday : Date,
    val uid : String,
    val docId : String?

)