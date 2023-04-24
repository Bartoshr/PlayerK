package io.github.bartoshr.playerK

sealed class Audio {
    object Empty : Audio()

    data class Data(
        val title: String = "",
        val audioSrc: String = "",
        val coverSrc: String = "",
        val authors: List<String> = emptyList(),
        val categories: List<String> = emptyList()
    ) : Audio()
}
