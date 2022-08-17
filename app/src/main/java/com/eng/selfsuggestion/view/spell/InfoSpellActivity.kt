package com.eng.selfsuggestion.view.spell

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityInfoSpellBinding
import com.eng.selfsuggestion.databinding.BottomSheetsBinding
import com.eng.selfsuggestion.repository.RoutineRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class InfoSpellActivity : AppCompatActivity() {

    lateinit var binding : ActivityInfoSpellBinding
    private lateinit var scopeIO : CoroutineScope

    var content : String? = null
    var date : String? = null
    var docId : String? = null
    var count : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoSpellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottom_sheet = findViewById<View>(R.id.bottom_sheet_layout)
        val bs_time = bottom_sheet.findViewById<TextView>(R.id.sheet_time_sub_txt)
        val bs_date = bottom_sheet.findViewById<TextView>(R.id.sheet_date_sub_txt)
        scopeIO = CoroutineScope(Dispatchers.IO)

        val intent = getIntent()
        content = intent.getStringExtra("content")
        date = intent.getStringExtra("date")
        count = intent.getLongExtra("count",0)
        docId = intent.getStringExtra("docId")

        binding.infospellContent.text = content
        bs_time.text = date
        bs_date.text = "you tried $count!"

        registerForContextMenu(binding.infospellMenuIcon)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?,
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        // menu file to object
        val inflater = menuInflater
        inflater.inflate(R.menu.top_infospell_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val routineRef = RoutineRepository

        // menu id 식별하여 이벤트 걸기
        when(item.itemId){
            R.id.menu_edit -> {
                // 수정하기
                val intent = Intent(baseContext, EditDailySpellActivity::class.java)
                intent.putExtra("docId",docId)
                intent.putExtra("content",content)
                startActivity(intent)
                overridePendingTransition(R.anim.translate_none, R.anim.translate_none)
            }
            R.id.menu_delete -> {
                // 삭제하기
                docId?.let {
                    routineRef.deleteRoutine(it).observe(this,{
                        if (it=="success"){
                            Toast.makeText(this, "success delete spell",
                                Toast.LENGTH_SHORT).show()
                            finish()
                            overridePendingTransition(R.anim.translate_none, R.anim.translate_none)
                        }else{
                            Toast.makeText(this, "fail delete spell",
                                Toast.LENGTH_SHORT).show()
                        }
                    })                }

            }
        }
        return super.onContextItemSelected(item)
    }
    
    // 그냥 클릭했을 시 안내메시지 보여주기
    fun clickToast(view : View){
        Toast.makeText(this, "it used long click",Toast.LENGTH_SHORT).show()
    }

    // 돌아가기
    fun finishActivity(view : View){
        finish()
        overridePendingTransition(R.anim.translate_none, R.anim.translate_none)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.translate_none,R.anim.translate_none)
    }
}