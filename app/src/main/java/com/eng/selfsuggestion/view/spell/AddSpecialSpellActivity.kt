package com.eng.selfsuggestion.view.spell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import com.eng.selfsuggestion.R
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
    var timedialog = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSpecialSpellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtSpellDate.setOnClickListener {
            val dialogFragment = SelectDateDialogFragment()
            dialogFragment.setOnClickListener { content, textValue ->
                binding.txtSpellDate.text = textValue
                scopeIO = CoroutineScope(Dispatchers.IO)
                content.year = content.year-1900
                targetdate = SimpleDateFormat("yyyy/MM/dd").format(content)
            }
            dialogFragment.show(supportFragmentManager, "SelectDateDialogFragment")
            timedialog = true
        }

        binding.btnCancel.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.translate_none, R.anim.translate_none)
        }

        binding.btnSave.setOnClickListener {
            if(!timedialog){
                Toast.makeText(this, "please add daily-mindspell",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(binding.edittextSpellName.text.isNotBlank()) {
                val spellName = binding.edittextSpellName.text
                Log.i("TAG", "onCreate: ?????? ????????????????????? ??????"+targetdate)
                if(!spellName.isBlank() && targetdate != null) {
                    val auth = FirebaseAuth.getInstance()
                    val date = Calendar.getInstance().time

                    val data = mapOf<String,Any>(
                        "content" to binding.edittextSpellName.text.toString(),
                        "timestamp" to date,
                        "arrivedate" to targetdate,
                        "uid" to auth.uid.toString()
                    )

                    // create firebase specialspell
                    val arriveRef = ArrivedRepository
                    arriveRef.createArrived(data).observe(this@AddSpecialSpellActivity,{
                        if (it=="success"){
                            Toast.makeText(this@AddSpecialSpellActivity, "success send your spell",
                                Toast.LENGTH_SHORT).show()
                            finish()
                            overridePendingTransition(R.anim.translate_none,R.anim.translate_none)
                        }else{
                            Toast.makeText(this@AddSpecialSpellActivity, "fail send your spell",
                                Toast.LENGTH_SHORT).show()
                        }

                    })
                } else {
                    Toast.makeText(this@AddSpecialSpellActivity, "please make your spell",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        return
    }
}