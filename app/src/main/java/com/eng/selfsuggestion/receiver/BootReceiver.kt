package com.eng.selfsuggestion.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val standard = Calendar.getInstance().apply { // 시간 임시저장
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 30)
        }
        // alarm 재설정
        if(intent.action == "android.intent.action.BOOT_COMPLETED"){
            var alarmMgr: AlarmManager? = null
            lateinit var alarmIntent: PendingIntent

            alarmMgr = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(context, UserAlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, 0, intent, 0)
            }
            alarmMgr?.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                standard.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
            )
        }
    }
}