package io.github.bartoshr.shared

data class Song(
    val title: String = "",
    val artist: String = "",
    val coverUrl: String = "",
    val audioUrl: String = "",
) {
    companion object {
        val default: Song
            get() = Song(
                title = "The Entertainer",
                artist = "Scott Joplin",
                coverUrl = "",
                audioUrl = ""
            )
    }
}
