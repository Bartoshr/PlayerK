package io.github.bartoshr.playerK

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration

internal object IosPlayerHandler : PlayerHandler {

    private val iosPlayer: AudioPlayer by lazy { IosPlayer() }

    override fun handle(playerBlock: AudioPlayer.() -> Unit) {
        return iosPlayer.playerBlock()
    }

    override fun <R> observe(
        period: Duration,
        playerBlock: AudioPlayer.() -> R,
    ): Flow<R> = flow {
        while (true) {
            emit(iosPlayer.playerBlock())
            delay(period)
        }
    }
}