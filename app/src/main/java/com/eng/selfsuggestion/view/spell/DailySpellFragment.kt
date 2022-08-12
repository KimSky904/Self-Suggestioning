package com.eng.selfsuggestion.view.spell

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.FragmentDailySpellBinding

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
        return _binding.root
    }
}