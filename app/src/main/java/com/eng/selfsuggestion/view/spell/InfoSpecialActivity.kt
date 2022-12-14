package com.eng.selfsuggestion.view.spell

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityInfoSpecialBinding
import com.eng.selfsuggestion.databinding.ActivityInfoSpellBinding
import com.eng.selfsuggestion.databinding.BottomSheetsBinding
import com.eng.selfsuggestion.repository.ArrivedRepository
import com.eng.selfsuggestion.repository.RoutineRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class InfoSpecialActivity : AppCompatActivity() {

    lateinit var binding : ActivityInfoSpecialBinding
    private lateinit var scopeIO : CoroutineScope
    val resultLuncher : ActivityResultLauncher<Intent> by lazy {
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            content = it.data?.getStringExtra("result")
        }
    }

    var content : String? = null
    var date : String? = null
    var docId : String? = null
    var arriveday : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoSpecialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottom_sheet = findViewById<View>(R.id.bottom_sheet_layout)
        val bs_time = bottom_sheet.findViewById<TextView>(R.id.sheet_time_sub_txt)
        val bs_date = bottom_sheet.findViewById<TextView>(R.id.sheet_date_sub_txt)
        scopeIO = CoroutineScope(Dispatchers.IO)

        val intent = getIntent()
        content = intent.getStringExtra("content")
        date = intent.getStringExtra("date")
        arriveday = intent.getStringExtra("arriveday")
        docId = intent.getStringExtra("docId")

        binding.infospellContent.text = content
        bs_time.text = date
        bs_date.text = arriveday

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
        val arriveRef = ArrivedRepository

        // menu id ???????????? ????????? ??????
        when(item.itemId){
            R.id.menu_edit -> {
                // ????????????
                val intent = Intent(baseContext, EditSpecialSpellActivity::class.java)
                intent.putExtra("docId",docId)
                intent.putExtra("content",content)
                intent.putExtra("arriveday",arriveday)

                resultLuncher.launch(intent)
                overridePendingTransition(R.anim.translate_none, R.anim.translate_none)
            }
            R.id.menu_delete -> {
                // ????????????
                docId?.let {
                    arriveRef.deleteArrived(it).observe(this,{
                        if (it=="success"){
                            Toast.makeText(this, "success delete spell",
                                Toast.LENGTH_SHORT).show()
                            finish()
                            overridePendingTransition(R.anim.translate_none, R.anim.translate_none)
                        }else{
                            Toast.makeText(this, "fail delete spell",
                                Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
        return super.onContextItemSelected(item)
    }

    // ?????? ???????????? ??? ??????????????? ????????????
    fun clickToast(view : View){
        Toast.makeText(this, "it used long click",Toast.LENGTH_SHORT).show()
    }

    // ????????????
    fun finishActivity(view : View){
        finish()
        overridePendingTransition(R.anim.translate_none, R.anim.translate_none)
    }

}