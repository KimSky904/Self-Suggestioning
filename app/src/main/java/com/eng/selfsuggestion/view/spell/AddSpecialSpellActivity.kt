package com.eng.selfsuggestion.view.spell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityAddSpecialSpellBinding
import com.eng.selfsuggestion.view.dialog.SelectDateDialogFragment
import com.eng.selfsuggestion.view.dialog.WriteSpellDialogFragment

class AddSpecialSpellActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddSpecialSpellBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSpecialSpellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtSpellDate.setOnClickListener {
            val dialogFragment = SelectDateDialogFragment()
            dialogFragment.show(supportFragmentManager, "SelectDateDialogFragment")
        }
    }
}