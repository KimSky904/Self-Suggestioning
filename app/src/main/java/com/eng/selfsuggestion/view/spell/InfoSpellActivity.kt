package com.eng.selfsuggestion.view.spell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityBaseBinding
import com.eng.selfsuggestion.databinding.ActivityInfoSpellBinding

class InfoSpellActivity : AppCompatActivity() {

    lateinit var binding : ActivityInfoSpellBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoSpellBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        // menu id 식별하여 이벤트 걸기
        when(item.itemId){
            R.id.menu_edit -> {
                // 수정하기
            }
            R.id.menu_delete -> {
                // 삭제하기
            }
        }
        return super.onContextItemSelected(item)
    }
    
    // 그냥 클릭했을 시 안내메시지 보여주기
    fun clickToast(view : View){
        Toast.makeText(this, "it used long click",Toast.LENGTH_SHORT).show()
    }

    // 그냥 클릭했을 시 안내메시지 보여주기
    fun finishActivity(view : View){
        finish()
    }
}