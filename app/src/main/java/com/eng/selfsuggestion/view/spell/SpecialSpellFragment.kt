package com.eng.selfsuggestion.view.spell

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.FragmentSpecialSpellBinding

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
        return _binding.root
    }
}