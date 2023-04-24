package io.github.bartoshr.playerK

import kotlin.time.Duration

data class TimePosition(
    val current: Duration = Duration.ZERO,
    val total: Duration = Duration.ZERO,
) {

    val fraction: Float
        get() {
            if (total <= Duration.ZERO) return 0f
            if (current <= Duration.ZERO) return 0f
            return (current / total).toFloat()
        }

    val remaining: Duration
        get() = total - current

    val formattedCurrentTime: String
        get() = current.format()

    val formattedTotalTime: String
        get() = total.format()

    val formattedRemainingTime: String
        get() = remaining.format()

    fun isPlaybackEnd(): Boolean =
        when {
            current == Duration.ZERO -> false
            total < Duration.ZERO -> false
            current >= total -> true
            else -> false
        }

    private fun Duration.format(): String =
        "${inWholeMinutes.padded()}:${inWholeSeconds.padded()}"

    private fun Long.padded(): String = toString().padStart(2, '0')
}
