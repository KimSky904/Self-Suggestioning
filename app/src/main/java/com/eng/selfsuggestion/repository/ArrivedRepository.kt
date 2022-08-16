package com.eng.selfsuggestion.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.eng.selfsuggestion.model.ArrivedModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// object(singleton pattern)
object ArrivedRepository {
    val db = FirebaseFirestore.getInstance()
    val auth = Firebase.auth
    val scopeIO = CoroutineScope(Dispatchers.IO)

    // create arrived
    fun createArrived(data : Map<String, Any>): MutableLiveData<String> {
        val result = MutableLiveData<String>()
        Log.i(TAG, "createRoutine: 스페셜생성함수 "+ RoutineRepository.auth.uid)
        // network coroutine scope
        scopeIO.launch {
            RoutineRepository.auth.uid?.let {
                Log.i(TAG, "createRoutine: 스페셜생성함수 내부"+data)
                RoutineRepository.db.collection("arrived").document(it).collection("users").document()
                    .set(data).addOnSuccessListener {
                        Log.i(TAG, "createRoutine: success create arrived"+data)
                        result.postValue("success")

                    }.addOnFailureListener {
                        Log.i(TAG, "createRoutine: "+it)
                        result.postValue("fail")
                    }
            }
        }

        return result
    }

    // get arrive message today
    fun getMessage(): MutableLiveData<ArrayList<ArrivedModel>> {
        Log.i(TAG, "getMessage: 도착한 메세지 가져오기 함수")
        val messages = ArrayList<ArrivedModel>()
        val Livemessages = MutableLiveData<ArrayList<ArrivedModel>>()
        val today = SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().time)

        scopeIO.launch {
            auth.uid?.let {
                Log.i(TAG, "getMessage: 도착한 메세지 가져오기 함수 auth"+today)

                db.collection("arrived")
                    .document(it).collection("users")
                    .whereEqualTo("arrivedate", today)
                    .orderBy("timestamp")
                    .addSnapshotListener(EventListener { value, error ->
                        if (value != null) {
                            messages.clear()
                            for (doc in value) {
                                messages.add(ArrivedModel(
                                    doc["content"] as String?,
                                    (doc["timestamp"] as Timestamp).toDate(),
                                    doc["arrivedate"] as String?,
                                    doc.id
                                ))
                            }
                            Log.i(TAG, "getMessage: 도착메세지 "+messages)
                            Livemessages.postValue(messages)
                        }
                    })
            }
        }
        return Livemessages
    }


    // get today arrive size
    fun getMessageSize(): MutableLiveData<Int> {
        Log.i(TAG, "getMessage: 도착한 메세지 가져오기 함수")
        val size = MutableLiveData<Int>(0)
        val today = SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().time)

        scopeIO.launch {
            auth.uid?.let {
                Log.i(TAG, "getMessage: 도착한 메세지 가져오기 함수 auth"+today)

                db.collection("arrived")
                    .document(it).collection("users")
                    .whereEqualTo("arrivedate", today)
                    .orderBy("timestamp")
                    .addSnapshotListener(EventListener { value, error ->
                        if (value != null) {
                            size.postValue(value.size())
                            Log.i(TAG, "getMessage: 도착메세지사이즈 "+value.size())
                        }
                    })
            }
        }
        return size
    }

    // get single arrive message
    suspend fun getArriveMessage(docId : String): ArrivedModel? {
        var message : ArrivedModel? = null
        auth.uid?.let { db.collection("arrived")
            .document(it).collection("users")
            .document(docId)
            .get()
            .addOnSuccessListener { doc ->
                message = ArrivedModel(
                    doc["content"] as String?,
                    (doc["timestamp"] as Timestamp).toDate(),
                    doc["arrvieday"] as String?,
                    doc.id
                )
            }
        }
        return message

    }

    // update
    fun modifyArrived(data:Map<String,Any>, docId:String): MutableLiveData<String> {
        val result = MutableLiveData<String>()

        scopeIO.launch {
            auth.uid?.let {
                db.collection("arrived")
                    .document(it).collection("users")
                    .document(docId)
                    .update(mapOf(
                        "content" to data["content"],
                        "arrivedate" to data["arrivedate"],
                    )).addOnSuccessListener {
                        Log.i(TAG, "ModifyRoutine: success update routine")
                        result.postValue("success")
                    }.addOnFailureListener{
                        result.postValue("fail")
                    }
            }
        }
        return result
    }

    // delete arrived
    fun deleteArrived(docId: String): MutableLiveData<String> {
        val result = MutableLiveData<String>()

        scopeIO.launch {
            RoutineRepository.auth.uid?.let {
                RoutineRepository.db.collection("arrived")
                    .document(it).collection("users")
                    .document(docId).delete().addOnSuccessListener {
                        result.postValue("success")
                    }.addOnFailureListener {
                        result.postValue("fail")
                    }
            }
        }
        return result
    }

}