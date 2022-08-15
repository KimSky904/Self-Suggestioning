package com.eng.selfsuggestion.view.spell

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.eng.selfsuggestion.adapter.DailySpellAdapter
import com.eng.selfsuggestion.databinding.FragmentDailySpellBinding
import com.eng.selfsuggestion.model.RoutineModel
import com.eng.selfsuggestion.model.TestingModel
import com.eng.selfsuggestion.repository.RoutineRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.EventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DailySpellFragment : Fragment() {

    private lateinit var _binding : FragmentDailySpellBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDailySpellBinding.inflate(inflater, container, false)

        // TODO : DELETE (TESTING)
        val one = TestingModel("2022-02-02","Daily Can Do It")
        val two = TestingModel("2022-02-03","Just Do It!")
        val three = TestingModel("2022-02-024","Just Do It")

        val scopeIO = CoroutineScope(Dispatchers.IO)
        val dailyadapter = DailySpellAdapter(context,parentFragmentManager)

        // get all routines array
        scopeIO.launch {
            val routines = ArrayList<RoutineModel>()

            RoutineRepository.auth.uid?.let {
                RoutineRepository.db.collection("routine")
                    .document(it).collection("users")
                    .orderBy("timestamp")
                    .addSnapshotListener(EventListener { value, error ->
                        if(value != null){
                            routines.clear()
                            for(doc in value){
                                routines.add(RoutineModel(
                                    doc["content"] as String?,
                                    doc["count"] as Long,
                                    (doc["timestamp"] as Timestamp).toDate(),
                                    doc.id
                                ))
                            }
                            Log.i(TAG, "onCreateView: 데일리 리스트 데이터 낱개"+routines)
                        }

                        dailyadapter.setDailyList(routines)
                    })
            }
        }

        _binding.listRecycleSpecial.adapter = dailyadapter
        _binding.listRecycleSpecial.layoutManager = LinearLayoutManager(context)

        return _binding.root
    }
}