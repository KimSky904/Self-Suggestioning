package com.eng.selfsuggestion.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.eng.selfsuggestion.model.RoutineModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object RoutineRepository {
    @SuppressLint("StaticFieldLeak")
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val scopeIO = CoroutineScope(Dispatchers.IO)

    // create routine
    fun createRoutine(data : Map<String, Any>): MutableLiveData<String> {
        val result = MutableLiveData<String>()
        Log.i(TAG, "createRoutine: 루틴생성함수 "+ auth.uid)
        // network coroutine scope
        scopeIO.launch {
            auth.uid?.let {
                Log.i(TAG, "createRoutine: 루틴생성함수 내부"+data)
                db.collection("routine").document(it).collection("users").document()
                    .set(data).addOnSuccessListener {
                        Log.i(TAG, "createRoutine: success create routine"+data)
                        result.postValue("success")

                    }.addOnFailureListener {
                        Log.i(TAG, "createRoutine: "+it)
                        result.postValue("fail")
                    }
            }
        }

        return result
    }

    // get single routine
    fun getRoutine(docId : String): MutableLiveData<RoutineModel> {
        Log.i(TAG, "getRoutine: 오늘의 데일리 가져오기 docId "+docId)
        val routine = MutableLiveData<RoutineModel>()

        scopeIO.launch {
            auth.uid?.let { db.collection("routine")
                .document(it).collection("users")
                .document(docId)
                .get()
                .addOnSuccessListener { doc ->
                    routine?.postValue(RoutineModel(
                        doc["content"] as String?,
                        doc["count"] as Long,
                        (doc["timestamp"] as Timestamp).toDate(),
                        doc.id
                    ))
                }
            }
        }

        return routine

    }


    // get random routine : return random elements list
    fun getRandomRoutine(): MutableLiveData<ArrayList<RoutineModel>> {
        val Liveroutines = MutableLiveData<ArrayList<RoutineModel>>()
        val routines = ArrayList<RoutineModel>()
        Log.i(TAG, "getRandomRoutine: routines 루틴 가져오는 함수")

        scopeIO.launch {
            RoutineRepository.auth.uid?.let {
                RoutineRepository.db.collection("routine")
                    .document(it).collection("users")
                    .orderBy("timestamp")
                    .addSnapshotListener(EventListener { value, error ->
                        if (value != null) {
                            routines.clear()
                            for (doc in value) {
                                routines.add(RoutineModel(
                                    doc["content"] as String?,
                                    doc["count"] as Long,
                                    (doc["timestamp"] as Timestamp).toDate(),
                                    doc.id
                                ))
                            }
                            Liveroutines.postValue(routines)
                        }
                    })

            }
        }

        return Liveroutines

    }

    // update count
    suspend fun modifyRoutine(data: RoutineModel, docId:String) {
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
    // update count
    fun countUp(data: RoutineModel, docId:String) {
        scopeIO.launch {
            auth.uid?.let {
                db.collection("routine")
                    .document(it).collection("users")
                    .document(docId)
                    .update(mapOf(
                        "count" to data.count+1
                    )).addOnSuccessListener {
                        Log.i(ContentValues.TAG, "ModifyRoutine: success update routine")
                    }
            }
        }
    }

    // delete routine
    suspend fun deleteRoutine(docId: String) {
        auth.uid?.let {
            db.collection("routine")
                .document(it).collection("users")
                .document(docId).delete()
        }
    }
}