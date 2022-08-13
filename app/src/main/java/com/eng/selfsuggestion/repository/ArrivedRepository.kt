package com.eng.selfsuggestion.repository

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.eng.selfsuggestion.model.ArrivedModel
import com.eng.selfsuggestion.model.RoutineModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import java.time.LocalDate
import kotlin.collections.ArrayList

// object(singleton pattern)
object ArrivedRepository {
    val db = FirebaseFirestore.getInstance()
    val auth = Firebase.auth

    // create arrived
    suspend fun createArrived(data : Map<String, Any>){
        auth.uid?.let {
            db.collection("arrived").document(it).collection("users").document()
                .set(data)
        }
    }

    // get arrive message today
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getMessage(): ArrayList<ArrivedModel> {
        val messages = ArrayList<ArrivedModel>()
        val today = LocalDate.now()
        auth.uid?.let {
            db.collection("arrived")
                .document(it).collection("users")
                .whereEqualTo("arriveday", today)
                .orderBy("timestamp")
                .addSnapshotListener(EventListener { value, error ->
                    if (value != null) {
                        messages.clear()
                        for (doc in value) {
                            messages.add(ArrivedModel(
                                doc["content"] as String?,
                                doc["timestamp"] as Timestamp,
                                doc["arrvieday"] as Date,
                                doc.id
                            ))
                        }
                    }
                })
        }
        return messages
    }

    // get all arrived array
    suspend fun getArrived() : ArrayList<ArrivedModel> {
        val messages = ArrayList<ArrivedModel>()

        auth.uid?.let {
            db.collection("arrived")
                .document(it).collection("users")
                .orderBy("timestamp")
                .addSnapshotListener(EventListener { value, error ->
                    if(value != null){
                        messages.clear()
                        for(doc in value){
                            messages.add(ArrivedModel(
                                doc["content"] as String?,
                                doc["timestamp"] as Timestamp,
                                doc["arrvieday"] as Date,
                                doc.id
                            ))
                        }
                    }
                })
        }

        return messages
    }

    // get single arrive message
    suspend fun getArriveMessage(docId : String): ArrivedModel? {
        var message : ArrivedModel? = null
        auth.uid?.let { db.collection("routine")
            .document(it).collection("users")
            .document(docId)
            .get()
            .addOnSuccessListener { doc ->
                message = ArrivedModel(
                    doc["content"] as String?,
                    doc["timestamp"] as Timestamp,
                    doc["arrvieday"] as Date,
                    doc.id
                )
            }
        }
        return message

    }

    // update count
    suspend fun ModifyArrived(data:ArrivedModel, docId:String) {
        auth.uid?.let {
            db.collection("arrived")
                .document(it).collection("users")
                .document(docId)
                .update(mapOf(
                    "content" to data.content,
                )).addOnSuccessListener {
                    Log.i(TAG, "ModifyRoutine: success update routine")
                }
        }
    }

    // delete arrived
    suspend fun DeleteArrived(docId: String) {
        auth.uid?.let {
            db.collection("arrived")
                .document(it).collection("users")
                .document(docId).delete()
        }
    }

}