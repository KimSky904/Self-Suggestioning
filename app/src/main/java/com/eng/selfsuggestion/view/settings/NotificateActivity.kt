package com.eng.selfsuggestion.view.settings

import android.animation.ObjectAnimator
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityNotificateBinding
import com.eng.selfsuggestion.receiver.UserAlarmReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class NotificateActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNotificateBinding
    private val pref : SharedPreferences by lazy { getSharedPreferences("pref", MODE_PRIVATE) }
    private var isStateOn : Boolean = false

    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(baseContext, R.anim.from_bottom_anim) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(baseContext, R.anim.to_bottom_anim) }
    private val defaultScope = CoroutineScope(Dispatchers.Default)

    var alarmMgr: AlarmManager? = null
    var alarmIntent: PendingIntent? = null
    
    // on / off 설정하기

    @RequiresApi(31)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificateBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initView()
        initNotification()

        // 이전에 설정했던 시간 가져오기
        val hour = pref.getInt("alarmhour",0)
        val minute = pref.getInt("alarmminute",0)

        binding.hourPicker.value = hour
        binding.minutePicker.value = minute

        binding.txtIndicatorOff.setOnClickListener {
            if(isStateOn) {
                Log.i(TAG, "onCreate: isState off")

                ObjectAnimator.ofFloat(binding.movingIndicator, "translationX", 0f).apply {
                    // time set 비활성화
                    binding.boxTimeSet.visibility = View.INVISIBLE
                    binding.boxTimeSet.startAnimation(toBottom)
                    duration = 600
                    start()
                }
                binding.txtIndicatorOff.postDelayed({
                    binding.txtIndicatorOn.setTextColor(Color.BLACK)
                    binding.txtIndicatorOff.setTextColor(Color.WHITE)
                }, 350)

                isStateOn = !isStateOn
            }
            alarmCencel()

            // alarm off setting
            with(pref.edit()){
                putBoolean("alarm",false)
                apply()
            }
        }
        binding.txtIndicatorOn.setOnClickListener {
            if(!isStateOn) {
                Log.i(TAG, "onCreate: isState on")
                ObjectAnimator.ofFloat(binding.movingIndicator, "translationX", 390f).apply {
                    // time set 활성화
                    binding.boxTimeSet.visibility = View.VISIBLE
                    binding.boxTimeSet.startAnimation(fromBottom)
                    duration = 600
                    start()
                }
                binding.txtIndicatorOn.postDelayed({
                    binding.txtIndicatorOff.setTextColor(Color.BLACK)
                    binding.txtIndicatorOn.setTextColor(Color.WHITE)
                }, 350)

                isStateOn = !isStateOn
            }
        }

        binding.btnBack.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, binding.hourPicker.value)
                set(Calendar.MINUTE, binding.minutePicker.value)
            }
            alarmSetting(calendar)

            // on / off, time 저장하기
            with(pref.edit()){
                putBoolean("alarm",isStateOn)
                putInt("alarmhour",binding.hourPicker.value)
                putInt("alarmminute",binding.minutePicker.value)
                apply()
            }
            Log.i(TAG, "initView: notification save"+pref.getBoolean("alarm",false))

            finish()
            overridePendingTransition(R.anim.translate_none,R.anim.translate_none)
        }
    }

    private fun initView() {

        binding.hourPicker.maxValue = 23
        binding.hourPicker.minValue = 0
        binding.hourPicker.wrapSelectorWheel = true
        binding.hourPicker.showDividers = LinearLayout.SHOW_DIVIDER_NONE
        binding.hourPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        val hourList = mutableListOf<String>()
        for(i in 0..23) {
            if(i/10 == 0) hourList.add("0$i")
            else hourList.add(i.toString())
        }
        binding.hourPicker.displayedValues = hourList.toTypedArray()

        binding.minutePicker.maxValue = 59
        binding.minutePicker.minValue = 0
        binding.minutePicker.wrapSelectorWheel = true
        binding.minutePicker.showDividers = LinearLayout.SHOW_DIVIDER_NONE
        binding.minutePicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        val minList = mutableListOf<String>()
        for(i in 0..59) {
            if(i/10 == 0) minList.add("0$i")
            else minList.add(i.toString())
        }
        binding.minutePicker.displayedValues = minList.toTypedArray()

    }

    // alarm setting
    // standard parameter : time set data by the user
    @RequiresApi(31)
    fun alarmSetting(standard : Calendar){
        defaultScope.launch {
            alarmMgr = this@NotificateActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(this@NotificateActivity, UserAlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(this@NotificateActivity, 0, intent, PendingIntent.FLAG_MUTABLE)
            }
            alarmMgr?.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                standard.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
            )

        }
        Toast.makeText(this@NotificateActivity,"Setting Alarm Time",Toast.LENGTH_SHORT).show()
    }

    private fun initNotification(){

        Log.i(TAG, "initNotification: 원래 상태 "+pref.getBoolean("alarm",false))
        if(!pref.getBoolean("alarm",false)) {
            ObjectAnimator.ofFloat(binding.movingIndicator, "translationX", 0f).apply {
                // time set 비활성화
                binding.boxTimeSet.visibility = View.INVISIBLE
                binding.boxTimeSet.startAnimation(toBottom)
                duration = 600
                start()
            }
            binding.txtIndicatorOff.postDelayed({
                binding.txtIndicatorOn.setTextColor(Color.BLACK)
                binding.txtIndicatorOff.setTextColor(Color.WHITE)
            }, 350)

            isStateOn = false
        }else{
            ObjectAnimator.ofFloat(binding.movingIndicator, "translationX", 390f).apply {
                // time set 활성화
                binding.boxTimeSet.visibility = View.VISIBLE
                binding.boxTimeSet.startAnimation(fromBottom)
                duration = 600
                start()
            }
            binding.txtIndicatorOn.postDelayed({
                binding.txtIndicatorOff.setTextColor(Color.BLACK)
                binding.txtIndicatorOn.setTextColor(Color.WHITE)
            }, 350)

            isStateOn = true
        }
    }

    private fun alarmCencel(){
        alarmMgr?.cancel(alarmIntent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.translate_none, R.anim.translate_none)
    }
}