package io.github.bartoshr.playerK

actual object PlayerK {
    actual fun getHandler() : PlayerHandler = IosPlayerHandler
}
