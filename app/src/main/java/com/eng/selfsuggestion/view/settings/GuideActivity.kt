package com.eng.selfsuggestion.view.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityGuideBinding

class GuideActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGuideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.translate_none, R.anim.translate_none)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.translate_none, R.anim.translate_none)
    }
}