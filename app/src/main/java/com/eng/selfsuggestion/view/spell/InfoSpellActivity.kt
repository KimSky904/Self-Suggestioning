package com.eng.selfsuggestion.view.spell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.inflate
import android.widget.Toast
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityAddDailySpellBinding.inflate
import com.eng.selfsuggestion.databinding.ActivityBaseBinding
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
    var count : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoSpellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val binding_ = BottomSheetsBinding.inflate(layoutInflater)
        scopeIO = CoroutineScope(Dispatchers.IO)

        val intent = getIntent()
        content = intent.getStringExtra("content")
        date = intent.getStringExtra("date")
        count = intent.getIntExtra("count",0)
        docId = intent.getStringExtra("docId")

        binding.infospellContent.text = content
        binding_.sheetTimeSubTxt.text = date
        binding_.sheetDateSubTxt.text = count.toString()+"번 하셨습니다."

        registerForContextMenu(binding.infospellMenuIcon)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
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
            }
            R.id.menu_delete -> {
                // 삭제하기
                scopeIO.launch {
                    docId?.let { routineRef.deleteRoutine(it) }
                }
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
    }
}