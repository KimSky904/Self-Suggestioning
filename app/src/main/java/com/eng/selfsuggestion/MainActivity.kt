package com.eng.selfsuggestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eng.selfsuggestion.databinding.ActivityMainBinding
import com.eng.selfsuggestion.view.navigation.BaseActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            val intent = Intent(this,BaseActivity::class.java)
            startActivity(intent)
        }

    }
}