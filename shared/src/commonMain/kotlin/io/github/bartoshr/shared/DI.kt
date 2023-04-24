package io.github.bartoshr.shared

import io.github.bartoshr.playerK.PlayerHandler
import io.github.bartoshr.playerK.PlayerK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object DI {
    private val coroutine by lazy { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
    private val playerHandler: PlayerHandler by lazy { PlayerK.getHandler() }
    val playerService: PlayerService by lazy { PlayerService(coroutine, playerHandler) }
}


