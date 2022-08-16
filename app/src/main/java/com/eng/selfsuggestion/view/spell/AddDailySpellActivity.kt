package com.eng.selfsuggestion.view.spell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityAddDailySpellBinding
import com.eng.selfsuggestion.repository.RoutineRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddDailySpellActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddDailySpellBinding
    private lateinit var scopeIO : CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDailySpellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scopeIO = CoroutineScope(Dispatchers.IO)
        val auth = FirebaseAuth.getInstance()
        val routineRef = RoutineRepository
        val date = Calendar.getInstance().time
        val str_date = SimpleDateFormat("yyyy/MM/dd").format(date)

        binding.btnCancel.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.translate_none,R.anim.translate_none)
        }

        binding.btnSave.setOnClickListener {
            if(binding.edittextSpellName.text.isNotBlank()) {
                Log.i("TAG", "onCreate: btn clicked"+binding.edittextSpellName.text)
                // create firebase routine
                val inputData = mapOf<String, Any>(
                    "content" to binding.edittextSpellName.text.toString(),
                    "count" to 0,
                    "timestamp" to date,
                    "uid" to auth.uid.toString()
                )


                routineRef.createRoutine(inputData).observe(this@AddDailySpellActivity,androidx.lifecycle.Observer{
                    if (it=="success"){
                        Toast.makeText(this@AddDailySpellActivity, "success make your spell",Toast.LENGTH_SHORT).show()
                        finish()
                        overridePendingTransition(R.anim.translate_none,R.anim.translate_none)
                    }else{
                        Toast.makeText(this@AddDailySpellActivity, "fail make your spell",Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this@AddDailySpellActivity, "please make your spell",Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onBackPressed() {
        return
    }
}