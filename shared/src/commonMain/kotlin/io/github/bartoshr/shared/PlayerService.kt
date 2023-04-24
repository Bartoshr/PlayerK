package io.github.bartoshr.shared

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import io.github.bartoshr.playerK.Audio
import io.github.bartoshr.playerK.PlayerHandler
import io.github.bartoshr.playerK.PlayerStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.seconds

class PlayerService constructor(
    scope: CoroutineScope,
    private val playerHandler: PlayerHandler,
) : CoroutineScope by scope {

    @NativeCoroutinesState
    val playerState = playerHandler.observe {
        PlayerState.default.copy(
            position = getPosition(),
            status = getStatus()
        )
    }.onEach { println(it.status) }

    fun togglePlay() {
        playerHandler.handle {

            if (getSource() == Audio.Empty) {
                setSource(Audio.Data(audioSrc = Song.default.audioUrl))
            }

            if (getPosition().isPlaybackEnd()) {
                moveTo(0.seconds)
            }

            when (getStatus()) {
                PlayerStatus.Playing -> pause()
                PlayerStatus.Idle -> play()
                PlayerStatus.Loading -> Unit
            }
        }
    }

    fun forward() {
        playerHandler.handle {
            moveTo(getPosition().current + DEFAULT_MOVE_BY.seconds)
        }
    }

    fun rewind() {
        playerHandler.handle {
            moveTo(getPosition().current - DEFAULT_MOVE_BY.seconds)
        }
    }

    companion object {
        const val DEFAULT_MOVE_BY = 5
    }
}
