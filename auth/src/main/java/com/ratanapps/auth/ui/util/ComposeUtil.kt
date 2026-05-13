package com.ratanapps.auth.ui.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect

object ComposeUtil {


    /**
     * Prints a debug level log message.
     *
     * @param tag The identifier for the log source.
     * @param message The text to be logged.
     */
    @Composable
    fun showDebugLogs(tag: String, message: String) {
        SideEffect {
            Log.d(tag, message)
        }
    }

    /**
     * Prints an error level log message.
     *
     * @param tag The identifier for the log source.
     * @param message The text to be logged.
     */
    @Composable
    fun showErrorLogs(tag: String, message: String) {
        SideEffect {
            Log.e(tag, message)
        }
    }

}