package com.nor35.photos.feature.photo.detail.domain.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.nor35.photos.domain.Constants.MEOW_NOTIFICATION_TAG
import com.nor35.photos.feature.photo.detail.R
import timber.log.Timber

class MeowBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
//        Toast.makeText(context, intent?.getStringExtra("action_msg"), Toast.LENGTH_SHORT).show()

//        val answer: Int = when (intent!!.getStringExtra(MEOW_NOTIFICATION_TAG)) {
//            MeowNotificationType.THANKS.name -> R.string.meow_thanks
//            else -> R.string.meow_rude_answer
//        }

        val answer: Int = when (intent!!.getStringExtra(MEOW_NOTIFICATION_TAG)) {
            MeowNotification.Companion.MeowNotificationType.THANKS.toString() -> R.string.meow_thanks
            else -> R.string.meow_rude_answer
        }

        Toast.makeText(context, context!!.getString(answer), Toast.LENGTH_LONG).show()
        Timber.d(context.getString(answer))
    }
}
