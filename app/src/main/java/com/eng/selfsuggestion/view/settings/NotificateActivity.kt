package com.eng.selfsuggestion.view.settings

import android.animation.ObjectAnimator
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
    private var isStateOn = false

    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(baseContext, R.anim.from_bottom_anim) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(baseContext, R.anim.to_bottom_anim) }
    private val defaultScope = CoroutineScope(Dispatchers.Default)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()


        binding.txtIndicatorOff.setOnClickListener {
            if(isStateOn) {
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
        }
        binding.txtIndicatorOn.setOnClickListener {
            if(!isStateOn) {
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
    fun alarmSetting(standard : Calendar){
        defaultScope.launch {
            var alarmMgr: AlarmManager? = null
            lateinit var alarmIntent: PendingIntent

            alarmMgr = this@NotificateActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(this@NotificateActivity, UserAlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(this@NotificateActivity, 0, intent, 0)
            }
            alarmMgr?.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                standard.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
            )

            Toast.makeText(this@NotificateActivity,"Setting Alarm Time",Toast.LENGTH_SHORT).show()
        }
    }
}