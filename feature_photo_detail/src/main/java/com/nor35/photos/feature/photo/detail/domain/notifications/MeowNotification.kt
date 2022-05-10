package com.nor35.photos.feature.photo.detail.domain.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.nor35.photos.domain.Constants.MEOW_ACTION
import com.nor35.photos.domain.Constants.MEOW_NOTIFICATION_TAG
import com.nor35.photos.feature.photo.detail.R
import timber.log.Timber

class MeowNotification {

    private var notificationManager: NotificationManager? = null

    companion object{

        const val MEOW_NOTIFICATION_CHANNEL_ID = "com.35.photos"

        enum class MeowNotificationType{
            THANKS, RUDE_ANSWER
        }

    }

    suspend fun invoke(context: Context, imageUrl: String) {

        Timber.d("MeowNotification: invoke")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannel(context)
        createNotification(context, imageUrl)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(context: Context) {

        val channel = NotificationChannel(
            MEOW_NOTIFICATION_CHANNEL_ID,
            context.getString(R.string.photos_notification),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = context.getString(R.string.i_want_to_eat)
        }

        notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager!!.createNotificationChannel(channel)
    }

    private suspend fun createNotification(context: Context, imageUrl: String) {

        val builder = NotificationCompat.Builder(context, MEOW_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(androidx.appcompat.R.drawable.btn_radio_on_mtrl)
            .setLargeIcon(getBitmap(context, imageUrl))
            .setContentTitle(context.getString(R.string.meow))
            .setContentText(context.getString(R.string.i_want_to_eat))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setShowWhen(true)
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    context.getString(R.string.i_want_to_eat)
                )
            )
           // .setDeleteIntent(getMeowBroadcastReceiver(context, MeowNotificationType.RUDE_ANSWER))

            .addAction(
                androidx.appcompat.R.drawable.abc_control_background_material,
                context.getString(R.string.kick_the_cat),
                getMeowBroadcastReceiver(context, MeowNotificationType.RUDE_ANSWER)
            )
            .addAction(
                androidx.appcompat.R.drawable.abc_control_background_material,
                context.getString(R.string.feed_the_cat),
                getMeowBroadcastReceiver(context, MeowNotificationType.THANKS)
            )
        notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager!!.notify(1, builder.build())
    }

    private suspend fun getBitmap(context: Context, imageUrl: String): Bitmap {

        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()

        val result = (loader.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private fun getMeowBroadcastReceiver(
        context: Context, meowNotificationType: MeowNotificationType): PendingIntent {

        val broadcastIntent = Intent(context, MeowBroadcastReceiver::class.java).apply {
            setPackage("com.nor35.photos")
            putExtra(MEOW_NOTIFICATION_TAG, meowNotificationType.toString())
            action = MEOW_ACTION
        }

        return PendingIntent.getBroadcast(context, 0, broadcastIntent, 0)
    }
}
