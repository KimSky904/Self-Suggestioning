package com.eng.selfsuggestion.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.eng.selfsuggestion.model.RoutineModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore

object RoutineRepository {
    @SuppressLint("StaticFieldLeak")
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    // create routine
    suspend fun createRoutine(data : Map<String, Any>){
        Log.i(TAG, "createRoutine: 루틴생성함수 ")
        auth.uid?.let {
            db.collection("routine").document(it).collection("users").document()
                .set(data).addOnSuccessListener {
                    Log.i(TAG, "createRoutine: success create routine"+data)
                }.addOnFailureListener {
                    Log.i(TAG, "createRoutine: "+it)
                }
        }
    }


    // get single routine
    suspend fun getRoutine(docId : String): RoutineModel? {
        var routine : RoutineModel? = null
        auth.uid?.let { db.collection("routine")
            .document(it).collection("users")
            .document(docId)
            .get()
            .addOnSuccessListener { doc ->
                routine = RoutineModel(
                    doc["content"] as String?,
                    doc["count"] as Int,
                    (doc["timestamp"] as Timestamp).toDate(),
                    doc.id
                )
            }
        }
        return routine

    }

    // update count
    suspend fun ModifyRoutine(data: RoutineModel, docId:String) {
        auth.uid?.let {
            db.collection("routine")
                .document(it).collection("users")
                .document(docId)
                .update(mapOf(
                    "content" to data.content,
                    "count" to data.count
                )).addOnSuccessListener {
                    Log.i(ContentValues.TAG, "ModifyRoutine: success update routine")
                }
        }
    }

    // delete routine
    suspend fun ModifyRoutine(docId: String) {
        auth.uid?.let {
            db.collection("routine")
                .document(it).collection("users")
                .document(docId).delete()
        }
    }
}