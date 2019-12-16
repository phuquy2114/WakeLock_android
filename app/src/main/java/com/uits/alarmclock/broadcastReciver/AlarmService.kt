package com.uits.alarmclock.broadcastReciver

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.uits.alarmclock.MainActivity
import com.uits.alarmclock.R
import java.util.*

class AlarmService : Service() {

    var media_song: MediaPlayer? = null
    var startId = 0
    var isRunning = false
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(
        intent: Intent,
        flags: Int,
        startId: Int
    ): Int { //Log.i("LocalService", "Received start id " + startId + ": " + intent);
// Fetch extra strings (on/off)
        var startId = startId
        val state = intent.extras!!.getString("extra")
        // Fetch sound_choice integer values
        val sound_id = intent.extras!!.getInt("sound_choice")
        // Log.e("Ringtone state: extra ", state);
// Log.e("Sound choice is ", sound_id.toString());
// Notification service setup, set intent to link to MainActivity
        val notify_manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent_main_activity =
            Intent(this.applicationContext, MainActivity::class.java)
        // Create pending intent
        val pending_intent_main_activity =
            PendingIntent.getActivity(this, 0, intent_main_activity, 0)
        // Set notification parameters
        var notify_popup: Notification? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notify_popup =
                Notification.Builder(this).setContentTitle("An alarm is going off!")
                    .setContentText("Click me!")
                    .setContentIntent(pending_intent_main_activity)
                    .setAutoCancel(true).build()
        }
        assert(state != null)
        when (state) {
            "alarm on" -> startId = 1
            "alarm off" -> {
                startId = 0
                Log.e("Start ID is ", state)
            }
            else -> startId = 0
        }
        if (!isRunning && startId == 1) { // If no music playing and alarm on pressed, music plays
// Log.e("there is no music, ", "and you want start");
            isRunning = true
            this.startId = 0
            // Set notification call commands
            notify_manager.notify(0, notify_popup)
            // Create instance of media player, start ringtone
            media_song = MediaPlayer.create(this, R.raw.kalimba)
            media_song?.start()
            // Play sound depending on passed sound_id
            if (sound_id == 0) { // Play randomly selected audio
                val min = 1
                val max = 5
                val rand = Random()
                val sound_number = rand.nextInt(max + min)
                // Log.e("Random number is ", String.valueOf(sound_number));
                if (sound_number == 1) {
                    media_song = MediaPlayer.create(this, R.raw.catalina_wine_mixer)
                    media_song?.start()
                } else if (sound_id == 2) {
                    media_song = MediaPlayer.create(this, R.raw.inclement_weather)
                    media_song?.start()
                } else if (sound_id == 3) {
                    media_song = MediaPlayer.create(this, R.raw.johnny_hopkins)
                    media_song?.start()
                } else if (sound_id == 4) {
                    media_song = MediaPlayer.create(this, R.raw.lightning_bolt)
                    media_song?.start()
                } else if (sound_id == 5) {
                    media_song = MediaPlayer.create(this, R.raw.robert_better_not_get_in_my_face)
                    media_song?.start()
                } else {
                    media_song = MediaPlayer.create(this, R.raw.butt_buddy)
                    media_song?.start()
                }
            } else if (sound_id == 1) {
                media_song = MediaPlayer.create(this, R.raw.catalina_wine_mixer)
                media_song?.start()
            } else if (sound_id == 2) {
                media_song = MediaPlayer.create(this, R.raw.inclement_weather)
                media_song?.start()
            } else if (sound_id == 3) {
                media_song = MediaPlayer.create(this, R.raw.johnny_hopkins)
                media_song?.start()
            } else if (sound_id == 4) {
                media_song = MediaPlayer.create(this, R.raw.lightning_bolt)
                media_song?.start()
            } else if (sound_id == 5) {
                media_song = MediaPlayer.create(this, R.raw.robert_better_not_get_in_my_face)
                media_song?.start()
            } else {
                media_song = MediaPlayer.create(this, R.raw.butt_buddy)
                media_song?.start()
            }
        } else if (isRunning && startId == 0) { // If music playing and alarm off pressed, music stops
// Log.e("there is music, ", "and you want end");
// Stop ringtone
            media_song!!.stop()
            media_song!!.reset()
            isRunning = false
            this.startId = 0
        } else if (!isRunning && startId == 0) { // Handles for when user presses buttons randomly
// If no music playing and alarm off pressed, do nothing
// Log.e("there is no music, ", "and you want end");
            isRunning = false
            startId = 0
        } else if (isRunning && startId == 1) { // If music playing and alarm on pressed, do nothing
// Log.e("there is music, ", "and you want start");
            isRunning = true
            this.startId = 1
        } else { // Catch overlooked edge cases
// Log.e("else, ", "somehow you reached this");
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() { // Tell user we stopped
// Log.e("onDestroy called", "ta da");
        super.onDestroy()
        isRunning = false
    }
}