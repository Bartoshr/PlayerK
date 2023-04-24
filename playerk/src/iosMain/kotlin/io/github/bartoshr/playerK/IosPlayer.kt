package io.github.bartoshr.playerK

import kotlinx.cinterop.useContents
import platform.AVFoundation.*
import platform.CoreMedia.CMTimeGetSeconds
import platform.CoreMedia.CMTimeMakeWithSeconds
import platform.Foundation.NSURL
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

class IosPlayer : AudioPlayer {

    private var player: AVPlayer = AVPlayer(playerItem = null)

    override fun setSource(audio: Audio.Data) {
        val url = NSURL(string = audio.audioSrc)
        val newItem = AVPlayerItem(url)
        player.replaceCurrentItemWithPlayerItem(newItem)
    }

    override fun clearSource() {
        player.replaceCurrentItemWithPlayerItem(null)
    }

    override fun play() {
        player.play()
    }

    override fun pause() {
        player.pause()
    }

    override fun moveTo(position: Duration) {
        val duration = player.currentItem?.duration()!!
        duration.useContents {
            val time = CMTimeMakeWithSeconds(
                position.toDouble(DurationUnit.SECONDS),
                timescale
            )
            player.seekToTime(time)
        }
    }

    override fun changeSpeed(playbackSpeed: Float) {
        player.rate = playbackSpeed
    }

    override fun getSource(): Audio =
        (player.currentItem?.asset as? AVURLAsset)?.URL?.absoluteString.toAudio()

    override fun getPosition(): TimePosition =
        TimePosition(getCurrentTime(), getTotalTime())

    private fun getCurrentTime(): Duration =
        player.currentItem?.let {
            CMTimeGetSeconds(it.currentTime()).toInt().seconds
        } ?: 0.seconds

    private fun getTotalTime(): Duration =
        player.currentItem?.let {
            CMTimeGetSeconds(it.asset.duration).toInt().seconds
        } ?: 0.seconds

    override fun getStatus(): PlayerStatus =
        when (player.timeControlStatus()) {
            AVPlayerTimeControlStatusPaused -> PlayerStatus.Idle
            AVPlayerTimeControlStatusPlaying -> PlayerStatus.Playing
            AVPlayerTimeControlStatusWaitingToPlayAtSpecifiedRate -> PlayerStatus.Loading
            else -> PlayerStatus.Idle
        }

    private fun String?.toAudio(): Audio =
        this?.let { Audio.Data(it) } ?: Audio.Empty

}