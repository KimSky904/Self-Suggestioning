package com.eng.selfsuggestion.view.spell

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.adapter.DailySpellAdapter
import com.eng.selfsuggestion.adapter.SpecialSpellAdapter
import com.eng.selfsuggestion.databinding.FragmentSpecialSpellBinding
import com.eng.selfsuggestion.model.ArrivedModel
import com.eng.selfsuggestion.model.RoutineModel
import com.eng.selfsuggestion.model.TestingModel
import com.eng.selfsuggestion.repository.ArrivedRepository
import com.eng.selfsuggestion.repository.RoutineRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.EventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpecialSpellFragment : Fragment() {

    private lateinit var _binding : FragmentSpecialSpellBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSpecialSpellBinding.inflate(inflater, container, false)

        // TODO : DELETE (TESTING)
        val one = TestingModel("2022-02-02","Special Can Do It")
        val two = TestingModel("2022-02-03","Just Do It!")
        val three = TestingModel("2022-02-024","Just Do It")
        val testingLIst : List<TestingModel> = listOf(one,two,three)

        val scopeIO = CoroutineScope(Dispatchers.IO)
        val specialadapter = SpecialSpellAdapter(context, parentFragmentManager )

        // get all routines array
        scopeIO.launch {
            val messages = ArrayList<ArrivedModel>()

            ArrivedRepository.auth.uid?.let {
                ArrivedRepository.db.collection("arrived")
                    .document(it).collection("users")
                    .orderBy("timestamp")
                    .addSnapshotListener(EventListener { value, error ->
                        if(value != null){
                            messages.clear()
                            for(doc in value){
                                messages.add(ArrivedModel(
                                    doc["content"] as String?,
                                    (doc["timestamp"] as Timestamp).toDate(),
                                    doc["arrviedate"] as String?,
                                    doc.id
                                ))
                            }
                            specialadapter.setSpecialList(messages)
                        }
                    })
            }
        }

        _binding.listRecycleSpecial.adapter = specialadapter
        _binding.listRecycleSpecial.layoutManager = LinearLayoutManager(context)


        return _binding.root
    }
}