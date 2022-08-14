package com.eng.selfsuggestion.view.spell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityAddToOthersSpellBinding

class AddToOthersSpellActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddToOthersSpellBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddToOthersSpellBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}