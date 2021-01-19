package kg.nurik.radio.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import kg.nurik.radio.BuildConfig
import kg.nurik.radio.R
import kg.nurik.radio.data.enum.NotificationClickTypes
import kg.nurik.radio.data.service.RadioService

object NotificationHelper {

    private const val CHANNEL_ID = "my_channel"
    private const val CHANNEL_NAME = "CHANNEL_NAME"
    private const val CHANNEL_DESC = "CHANNEL_DESC"
    private const val EXTRA_ACTION = "EXTRA_ACTION"

    fun createNotification(context: Context): Notification? {
        createNotificationChannel(context) //канал для уведомления для каких то андроди и выше

        val notificationLayout = RemoteViews(BuildConfig.APPLICATION_ID, R.layout.view_notification)

        notificationLayout.setOnClickPendingIntent(
            R.id.imgPause, getPendingIntentToService(context, NotificationClickTypes.PLAY)
        )
        notificationLayout.setOnClickPendingIntent(
            R.id.imgNext, getPendingIntentToService(context, NotificationClickTypes.NEXT)
        )
        notificationLayout.setOnClickPendingIntent(
            R.id.imgBack, getPendingIntentToService(context, NotificationClickTypes.PREV)
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID) // создание уведомления
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCustomContentView(notificationLayout) // чтоб в уведомлении своя касотмная верстка
            .setSmallIcon(R.drawable.exo_notification_small_icon)
        return builder.build()
    }

    private fun getPendingIntentToService(context: Context, type: NotificationClickTypes) =
        PendingIntent.getService(
            context,
            type.ordinal,
            Intent(context, RadioService::class.java).apply {
                action = type.name
            },
            0
        )

    private fun createNotificationChannel(context: Context) { // канал
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            channel.description = CHANNEL_DESC
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}