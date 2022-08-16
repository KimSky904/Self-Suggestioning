package com.eng.selfsuggestion.view.navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eng.selfsuggestion.BuildConfig
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.FragmentSettingBinding
import com.eng.selfsuggestion.view.settings.GuideActivity
import com.eng.selfsuggestion.view.settings.NotificateActivity
import com.eng.selfsuggestion.view.settings.PremiumActivity
import com.eng.selfsuggestion.view.spell.AddToOthersSpellActivity

class SettingFragment : Fragment() {

    lateinit var _binding : FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        _binding.txtVersion.text = BuildConfig.VERSION_NAME

        _binding.itemNotification.setOnClickListener {
            val intent = Intent(activity, NotificateActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
        _binding.itemPremium.setOnClickListener {
            val intent = Intent(activity, PremiumActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
        _binding.moveGuide.setOnClickListener {
            val intent = Intent(activity, GuideActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        return _binding.root
    }
}