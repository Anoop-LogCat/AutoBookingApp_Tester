package production.logcat.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager.getNotificationChannel(getString(R.string.notification_channel_default))==null) {
            val audioAttributes = AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).setUsage(
                AudioAttributes.USAGE_NOTIFICATION_RINGTONE).build()
            val name = "Auto Tester Notification"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(getString(R.string.notification_channel_default), name, importance)
            mChannel.enableVibration(true)
            mChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),audioAttributes)
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}