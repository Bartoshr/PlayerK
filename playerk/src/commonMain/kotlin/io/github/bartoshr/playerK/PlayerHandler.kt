package io.github.bartoshr.playerK

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

interface PlayerHandler {
    fun handle(playerBlock: AudioPlayer.() -> Unit)

    fun <R> observe(
        period: Duration = 0.5.seconds,
        playerBlock: AudioPlayer.() -> R,
    ): Flow<R>
}
