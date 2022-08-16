package com.eng.selfsuggestion.view.spell

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityEditSpecialSpellBinding
import com.eng.selfsuggestion.repository.ArrivedRepository
import com.eng.selfsuggestion.view.dialog.SelectDateDialogFragment
import com.eng.selfsuggestion.view.navigation.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat

class EditSpecialSpellActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditSpecialSpellBinding
    private lateinit var targetdate : String
    private lateinit var scopeIO : CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditSpecialSpellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent

        intent.putExtra("result",binding.edittextSpellName.text.toString())

        val docId = intent.getStringExtra("docId")
        val content = intent.getStringExtra("content")
        val arriveday = intent.getStringExtra("arriveday")

        // 이전 데이터 보여주기
        binding.edittextSpellName.setText(content)
        binding.txtSpellDate.text = arriveday
        if (arriveday != null) {
            targetdate = arriveday
        }

        Log.i("TAG", "onCreate: 스페셜 이전 데이터"+content+arriveday)

        binding.txtSpellDate.setOnClickListener {
            val dialogFragment = SelectDateDialogFragment()
            dialogFragment.setOnClickListener { content, textValue ->
                binding.txtSpellDate.text = textValue
                scopeIO = CoroutineScope(Dispatchers.IO)
                content.year = content.year-1900
                targetdate = SimpleDateFormat("yyyy/MM/dd").format(content)
            }
            dialogFragment.show(supportFragmentManager, "SelectDateDialogFragment")
        }

        binding.btnSave.setOnClickListener {
            val arriveRef = ArrivedRepository
            if (docId != null) {
                arriveRef.modifyArrived(mapOf<String,Any>(
                    "content" to binding.edittextSpellName.text.toString(),
                    "arrivedate" to targetdate
                ),docId).observe(this,{
                    if (it=="success"){
                        Toast.makeText(this, "success modify spell",
                            Toast.LENGTH_SHORT).show()
                        finish()
                        overridePendingTransition(R.anim.translate_none, R.anim.translate_none)
                    }else{
                        Toast.makeText(this, "fail modify spell",
                            Toast.LENGTH_SHORT).show()
                    }
                })
            }
            setResult(Activity.RESULT_OK)
            finish()
            overridePendingTransition(R.anim.translate_none, R.anim.translate_none)
        }
        binding.btnCancel.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.translate_none, R.anim.translate_none)
        }

    }
}