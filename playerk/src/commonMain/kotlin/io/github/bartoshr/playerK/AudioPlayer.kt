package io.github.bartoshr.playerK

import kotlin.time.Duration

interface AudioPlayer {
    fun setSource(audio: Audio.Data)
    fun getSource(): Audio
    fun clearSource()
    fun play()
    fun pause()
    fun moveTo(position: Duration)
    fun changeSpeed(playbackSpeed: Float)
    fun getPosition(): TimePosition
    fun getStatus(): PlayerStatus
}