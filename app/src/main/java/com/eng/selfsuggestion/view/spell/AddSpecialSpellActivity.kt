package com.eng.selfsuggestion.view.spell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import com.eng.selfsuggestion.databinding.ActivityAddSpecialSpellBinding
import com.eng.selfsuggestion.repository.ArrivedRepository
import com.eng.selfsuggestion.repository.RoutineRepository
import com.eng.selfsuggestion.view.dialog.SelectDateDialogFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class AddSpecialSpellActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddSpecialSpellBinding
    private lateinit var targetdate : String
    private lateinit var scopeIO : CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSpecialSpellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtSpellDate.setOnClickListener {
            val dialogFragment = SelectDateDialogFragment()
            dialogFragment.setOnClickListener { content, textValue ->
                binding.txtSpellDate.text = textValue
                scopeIO = CoroutineScope(Dispatchers.IO)
                targetdate = SimpleDateFormat("yyyy/MM/dd").format(content)
            }
            dialogFragment.show(supportFragmentManager, "SelectDateDialogFragment")
        }

        binding.btnSave.setOnClickListener {
            // spell_name : spell name
            // time_picker : 사용자가 입력한 Date, 위에서 형변환 필요
            val spellName = binding.edittextSpellName.text
            if(!spellName.isBlank() && targetdate != null) {
                // TODO : firebase에 저장~!
                val auth = FirebaseAuth.getInstance()
                val date = Calendar.getInstance().time

                val data = mapOf<String,Any>(
                    "contents" to binding.edittextSpellName.text.toString(),
                    "count" to 0,
                    "timestamp" to date,
                    "arrivedate" to targetdate,
                    "uid" to auth.uid.toString()
                )

                // create firebase specialspell
                scopeIO.launch {
                    val arriveRef = ArrivedRepository
                    arriveRef.createArrived(data)
                }
            }
        }
    }
}