package com.eng.selfsuggestion.repository

import com.eng.selfsuggestion.model.RoutineModel
import com.eng.selfsuggestion.model.SendingModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

object SendingRepository {
    val db = FirebaseFirestore.getInstance()
    val auth = Firebase.auth

    // get all routines array
    suspend fun getSendings() : ArrayList<SendingModel>{
        val sendings = ArrayList<SendingModel>()

        auth.uid?.let {
            db.collection("sending")
                .orderBy("timestamp")
                .addSnapshotListener(EventListener { value, error ->
                    if(value != null){
                        sendings.clear()
                        for(doc in value){
                            sendings.add(SendingModel(
                                doc["content"] as String?,
                                doc["timestamp"] as Timestamp,
                                doc["uid"] as String,
                                doc.id
                            ))
                        }
                    }
                })
        }

        return sendings
    }

    // create sending

    // delete sending
}