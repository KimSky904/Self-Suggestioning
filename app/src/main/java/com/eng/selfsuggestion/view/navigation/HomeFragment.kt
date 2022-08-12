package com.eng.selfsuggestion.view.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eng.selfsuggestion.databinding.FragmentHomeBinding
import com.eng.selfsuggestion.view.dialog.WriteSpellDialogFragment

class HomeFragment : Fragment() {

    private lateinit var _binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding.btnWriteSpell.setOnClickListener {
            val dialogFragment = WriteSpellDialogFragment()
            dialogFragment.show(parentFragmentManager, "WriteSpellDialogFragment")

        }
        return _binding.root
    }
}