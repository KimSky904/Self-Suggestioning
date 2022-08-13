package com.eng.selfsuggestion.view.spell

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.adapter.SpecialSpellAdapter
import com.eng.selfsuggestion.databinding.FragmentSpecialSpellBinding
import com.eng.selfsuggestion.model.TestingModel

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
        _binding.listRecycleSpecial.adapter = SpecialSpellAdapter(testingLIst)
        _binding.listRecycleSpecial.layoutManager = LinearLayoutManager(context)

        return _binding.root
    }
}