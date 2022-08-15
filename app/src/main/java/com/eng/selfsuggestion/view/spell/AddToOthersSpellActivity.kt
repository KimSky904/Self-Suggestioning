package com.eng.selfsuggestion.view.spell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityAddToOthersSpellBinding
import com.eng.selfsuggestion.repository.RoutineRepository
import com.eng.selfsuggestion.repository.SendingRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddToOthersSpellActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddToOthersSpellBinding
    private lateinit var scopeIO : CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddToOthersSpellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scopeIO = CoroutineScope(Dispatchers.IO)
        val auth = FirebaseAuth.getInstance()
        val sendingRef = SendingRepository
        val date = Calendar.getInstance().time

        binding.btnSave.setOnClickListener {
            Log.i("TAG", "onCreate: btn clicked"+binding.edittextSpellName.text)
            // create firebase routine
            val inputData = mapOf<String, Any>(
                "content" to binding.edittextSpellName.text.toString(),
                "timestamp" to date,
                "uid" to auth.uid.toString()
            )

            // network coroutine scope
            scopeIO.launch {
                Log.i("TAG", "onCreate: scope IO 실행"+inputData)
                sendingRef.createSending(inputData)
            }
        }
    }
}