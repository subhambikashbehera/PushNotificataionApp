package com.enrivers.pushnotificataionapp

import android.app.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.net.URL

class FireBasePushNotificationService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val image = message.data["image"].toString()
        val text = message.data["text"].toString()
        val notificationVisibility = message.data["notificationVisibility"].toString()

        val featuresAvailable_1 = message.data["featuresAvailable_1"].toString()
        val featuresAvailable_2 = message.data["featuresAvailable_2"].toString()
        val featuresAvailable_3 = message.data["featuresAvailable_3"].toString()
        val featuresAvailable_4 = message.data["featuresAvailable_4"].toString()
        val sharedPreferencesObject  = SharedPreferanceManager(applicationContext)
        sharedPreferencesObject.saveData("image",image)
        sharedPreferencesObject.saveData("text",text)
        sharedPreferencesObject.saveData("featuresAvailable_1",featuresAvailable_2)
        sharedPreferencesObject.saveData("featuresAvailable_2",featuresAvailable_2)
        sharedPreferencesObject.saveData("featuresAvailable_3",featuresAvailable_3)
        sharedPreferencesObject.saveData("featuresAvailable_4",featuresAvailable_4)
        val url = URL(image)
        val  bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        if (notificationVisibility=="true"){
            showNotification(bitmap,text)
        }

    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun showNotification(bitmap: Bitmap,title:String) {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, MainActivity::class.java)
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntentWithParentStack(intent)
        val contentIntent = stackBuilder
            .getPendingIntent(
                0, PendingIntent.FLAG_UPDATE_CURRENT
                        or PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", "Anil", importance).apply {
                description = "Notification Description"
            }
            // Register the channel with the system
            manager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Test Notification")
            .setContentText(title)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(title))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setLargeIcon(bitmap)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setContentIntent(contentIntent)
        val notification = builder.build()
        notification.flags =
            notification.flags or (Notification.FLAG_ONGOING_EVENT or Notification.FLAG_NO_CLEAR)
        manager.notify(1000, notification)
    }




}