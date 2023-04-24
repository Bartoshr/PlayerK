package io.github.bartoshr.playerK

import android.net.Uri
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class AndroidPlayer(
    private val mediaController: MediaController,
    scope: CoroutineScope,
) : AudioPlayer, CoroutineScope by scope {

    override fun setSource(audio: Audio.Data) {
        launch {
            val rmd = MediaItem.RequestMetadata.Builder()
                .setMediaUri(audio.audioSrc.toUri())
                .build()

            val mmd = MediaMetadata.Builder()
                .setTitle(audio.title)
                .setArtworkUri(audio.coverSrc.toUri())
                .build()

            val mediaItem = MediaItem.Builder()
                .setRequestMetadata(rmd)
                .setMediaMetadata(mmd)
                .build()

            mediaController.setMediaItem(mediaItem)
            mediaController.prepare()
        }
    }

    override fun clearSource() {
        launch {
            mediaController.clearMediaItems()
        }
    }

    override fun play() {
        launch {
            if (mediaController.playerError != null) {
                mediaController.prepare()
            }
            mediaController.play()
        }
    }

    override fun pause() {
        launch {
            mediaController.pause()
        }
    }

    override fun moveTo(position: Duration) {
        launch {
            mediaController.seekTo(0, position.inWholeMilliseconds)
        }
    }

    override fun changeSpeed(playbackSpeed: Float) {
        mediaController.setPlaybackSpeed(playbackSpeed)
    }

    override fun getSource(): Audio =
        mediaController.currentMediaItem
            ?.requestMetadata?.mediaUri?.toAudio() ?: Audio.Empty

    override fun getPosition(): TimePosition =
        with(mediaController) {
            if (isConnected) {
                TimePosition(
                    currentPosition.milliseconds,
                    duration.milliseconds.negativeToZero()
                )
            } else {
                TimePosition()
            }
        }

    private fun Duration.negativeToZero(): Duration {
        return if (this < 0.seconds) {
            0.seconds
        } else {
            this
        }
    }

    override fun getStatus(): PlayerStatus {
        return mapToPlayerState(mediaController)
    }


    private fun Uri?.toAudio(): Audio =
        this?.let { Audio.Data(it.toString()) } ?: Audio.Empty

    private fun mapToPlayerState(controller: MediaController) = when {
        controller.isPlaying -> PlayerStatus.Playing
        controller.isLoading -> PlayerStatus.Loading
        else -> PlayerStatus.Idle
    }

}