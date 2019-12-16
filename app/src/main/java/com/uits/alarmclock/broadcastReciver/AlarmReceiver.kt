package com.uits.alarmclock.broadcastReciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        // Fetch extra strings from MainActivity on button intent
        // Determines whether user presses on or off
        // Fetch extra strings from MainActivity on button intent
        // Determines whether user presses on or off
        val fetch_string = intent!!.extras!!.getString("extra")
        // Log.e("What is the key?", fetch_string);

        // Fetch extra longs from MainActivity intent
        // Tells which value user selects from spinner
        // Log.e("What is the key?", fetch_string);

        val get_sound_choice = intent.extras!!.getInt("sound_choice")

        val service_intent = Intent(context, AlarmService::class.java)

        // Pass extra string from receiver to RingtonePlayingService
        // Pass extra string from receiver to RingtonePlayingService
        service_intent.putExtra("extra", fetch_string)

        // Pass extra integer from receiver to RingtonePlayingService
        // Pass extra integer from receiver to RingtonePlayingService
        service_intent.putExtra("sound_choice", get_sound_choice)

        // Start ringtone service
        // Start ringtone service
        context!!.startService(service_intent)
    }

}