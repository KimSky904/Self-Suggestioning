package com.eng.selfsuggestion.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.eng.selfsuggestion.model.RoutineModel
import com.eng.selfsuggestion.model.SendingModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SendingRepository {
    val db = FirebaseFirestore.getInstance()
    val auth = Firebase.auth
    val scopeIO = CoroutineScope(Dispatchers.IO)

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
                                (doc["timestamp"] as Timestamp).toDate(),
                                doc["uid"] as String,
                                doc.id
                            ))
                        }
                        Log.i("TAG", "getSendings: 스페셜 SendingRef > getSending"+sendings)
                    }
                })
        }

        return sendings
    }

    // get random sendings : return random elements list
    fun getRandomSending(): MutableLiveData<SendingModel> {
        val sendings = ArrayList<SendingModel>()
        val Livesending = MutableLiveData<SendingModel>()
        Log.i("TAG", "getRandomSending: 스페셜 랜덤 함수 데이터"+sendings)

        scopeIO.launch {
            auth.uid?.let {
                db.collection("sending")
                    .orderBy("timestamp")
                    .addSnapshotListener(EventListener { value, error ->
                        if(value != null){
                            sendings.clear()
                            for(doc in value){
                                sendings.add(SendingModel(
                                    doc["content"] as String?,
                                    (doc["timestamp"] as Timestamp).toDate(),
                                    doc["uid"] as String,
                                    doc.id
                                ))
                            }
                            val random = (0..(sendings.size-1)).random()  // 1 <= n <= 20
                            Livesending.postValue(sendings.get(random))
                            Log.i("TAG", "getSendings: 스페셜 SendingRef > getSending"+sendings)
                        }
                    })
            }
        }

        return Livesending
    }

    // create sending
    fun createSending(data:Map<String, Any>): MutableLiveData<String> {
        val result = MutableLiveData<String>()

        scopeIO.launch {
            db.collection("sending").document().set(data).addOnSuccessListener {
                result.postValue("success")
            }.addOnFailureListener {
                result.postValue("fail")
            }
        }

        return result
    }

    // delete sending
    suspend fun deleteSending(docId:String) {
        db.collection("sending").document(docId).delete()
    }
}