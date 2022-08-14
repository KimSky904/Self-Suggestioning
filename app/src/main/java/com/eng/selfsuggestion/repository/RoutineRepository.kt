package com.eng.selfsuggestion.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import com.eng.selfsuggestion.model.RoutineModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.HashMap

object RoutineRepository {
    @SuppressLint("StaticFieldLeak")
    val db = FirebaseFirestore.getInstance()
    val auth = Firebase.auth

    // create routine
    suspend fun createRoutine(data : HashMap<String, Comparable<Any>>){
        auth.uid?.let {
            db.collection("routine").document(it).collection("users").document()
                .set(data)
        }
    }

    // get all routines array
    suspend fun getRoutines() : ArrayList<RoutineModel>{
        val routines = ArrayList<RoutineModel>()

        auth.uid?.let {
            db.collection("routine")
                .document(it).collection("users")
                .orderBy("timestamp")
                .addSnapshotListener(EventListener { value, error ->
                    if(value != null){
                        routines.clear()
                        for(doc in value){
                            routines.add(RoutineModel(
                                doc["content"] as String?,
                                doc["count"] as Int,
                                doc["timestamp"] as Timestamp,
                                doc.id
                            ))
                        }
                    }
                })
        }

        return routines
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
                    doc["timestamp"] as Timestamp,
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