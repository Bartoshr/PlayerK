package io.github.bartoshr.shared

import io.github.bartoshr.playerK.PlayerStatus
import io.github.bartoshr.playerK.TimePosition

data class PlayerState(
    val status: PlayerStatus = PlayerStatus.Idle,
    val position: TimePosition = TimePosition(),
    val song: Song = Song.default,
) {

    companion object {
        val default: PlayerState
            get() = PlayerState()
    }

}
