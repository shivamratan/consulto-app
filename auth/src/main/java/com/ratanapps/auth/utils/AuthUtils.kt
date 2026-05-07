package com.ratanapps.auth.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object AuthUtils {

    /**
     * Generates the current timestamp in a specific format and timezone.
     *
     * @return A formatted string representing the current time.
     */
    fun getCurrentTimeStamp(): String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat(
            "dd MMMM yyyy, HH:mm z",
            Locale.ENGLISH
        )

        formatter.timeZone = TimeZone.getTimeZone("Asia/Kolkata")

        val formattedDate = formatter.format(calendar.time)
        return formattedDate;
    }

    /**
     * Displays a long-duration Toast message.
     *
     * @param context The context used to display the Toast.
     * @param message The text message to show.
     */
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}