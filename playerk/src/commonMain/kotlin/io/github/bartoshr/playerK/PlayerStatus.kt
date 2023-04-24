package io.github.bartoshr.playerK

enum class PlayerStatus {
    Idle, Playing, Loading
}

fun PlayerStatus.isPlaying() = this == PlayerStatus.Playing
fun PlayerStatus.isIdle() = this == PlayerStatus.Idle
fun PlayerStatus.isLoading() = this == PlayerStatus.Loading