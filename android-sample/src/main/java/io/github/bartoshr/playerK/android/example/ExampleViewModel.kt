package io.github.bartoshr.playerK.android.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.bartoshr.playerK.PlayerStatus
import io.github.bartoshr.shared.DI
import io.github.bartoshr.shared.PlayerService
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExampleViewModel(
    private val playerService: PlayerService = DI.playerService,
) : ViewModel() {

    val state: StateFlow<PlayerUiState> = playerService.playerState.map {
        PlayerUiState(
            status = it.status,
            title = it.song.title,
            artist = it.song.artist,
            coverUrl = it.song.coverUrl,
            currentTime = it.position.formattedCurrentTime,
            totalTime = it.position.formattedTotalTime
        )
    }.stateIn(viewModelScope, WhileSubscribed(), PlayerUiState())

    fun togglePlay() = viewModelScope.launch {
        playerService.togglePlay()
    }

    fun forward() = viewModelScope.launch {
        playerService.forward()
    }

    fun rewind() = viewModelScope.launch {
        playerService.rewind()
    }
}

data class PlayerUiState(
    val status: PlayerStatus = PlayerStatus.Idle,
    val currentTime: String = "",
    val totalTime: String = "",
    val title: String = "",
    val artist: String = "",
    val coverUrl: String = "",
)
