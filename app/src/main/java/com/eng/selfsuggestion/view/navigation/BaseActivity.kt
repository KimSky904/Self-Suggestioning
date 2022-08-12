package com.eng.selfsuggestion.view.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityBaseBinding
import com.eng.selfsuggestion.view.spell.AddDailySpellFragment
import com.eng.selfsuggestion.view.spell.AddSpecialSpellFragment
import com.eng.selfsuggestion.view.spell.AddToOthersSpellFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BaseActivity : AppCompatActivity() {

    lateinit var binding : ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        binding.bottomNavigation.selectedItemId = R.id.action_home
        setContentView(binding.root)

        replaceFragment(HomeFragment())
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.action_list -> {
                    Log.e("TAG","List")
                    replaceFragment(ListFragment())
                    true
                }
                R.id.action_home -> {
                    Log.e("TAG","Home")
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.action_settings -> {
                    Log.e("TAG","Settings")
                    replaceFragment(SettingFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment : Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame,fragment)
        fragmentTransaction.commit()
    }
}