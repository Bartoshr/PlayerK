package io.github.bartoshr.playerK

import androidx.activity.ComponentActivity

actual object PlayerK {

    actual fun getHandler() : PlayerHandler = AndroidPlayerHandler

    fun connect(activity: ComponentActivity) {
        AndroidPlayerConnector(activity)
    }

}