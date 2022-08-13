package com.eng.selfsuggestion.view.settings

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.eng.selfsuggestion.receiver.UserAlarmReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class NotifyFragment : Fragment() {
    val defaultScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    // alarm setting
    // standard parameter : time set data by the user
    fun alarmSetting(standard : Calendar){
        defaultScope.launch {
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