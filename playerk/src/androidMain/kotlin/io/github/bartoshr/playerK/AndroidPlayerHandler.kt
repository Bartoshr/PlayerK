package io.github.bartoshr.playerK

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.time.Duration

internal object AndroidPlayerHandler : PlayerHandler {

    private var audioPlayer: AudioPlayer? = null

    fun registerPlayer(audioPlayer: AudioPlayer?) {
        AndroidPlayerHandler.audioPlayer = audioPlayer
    }

    fun unregisterPlayer() {
        audioPlayer = null
    }

    override fun handle(playerBlock: AudioPlayer.() -> Unit) {
        return audioPlayer?.playerBlock()
            ?: throw IllegalStateException("Invoke playerK connect method first.")
    }

    override fun <R> observe(
        period: Duration,
        playerBlock: AudioPlayer.() -> R,
    ): Flow<R> = flow {
        while (true) {
            audioPlayer?.let {
                emit(it.playerBlock())
            }
            delay(period)
        }
    }.flowOn(Dispatchers.Main)
}