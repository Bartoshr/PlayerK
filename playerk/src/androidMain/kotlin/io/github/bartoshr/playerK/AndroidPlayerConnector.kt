package io.github.bartoshr.playerK

import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.CoroutineScope

internal class AndroidPlayerConnector constructor(
    private val activity: ComponentActivity,
) : DefaultLifecycleObserver {

    init {
        activity.lifecycle.addObserver(this)
    }

    private lateinit var controllerFuture: ListenableFuture<MediaController>

    private fun disconnectPlayerService() {
        MediaController.releaseFuture(controllerFuture)
        AndroidPlayerHandler.unregisterPlayer()
    }

    private fun connectPlayerService(context: Context, coroutineScope: CoroutineScope) {
        val component = ComponentName(context, PlayerService::class.java)
        val sessionToken = SessionToken(context, component)
        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                controllerFuture.get()
                    ?.let {
                        Log.d("PlayerK", "New media controller created $it")
                        AndroidPlayerHandler.registerPlayer(AndroidPlayer(it, coroutineScope))
                    }
            },
            MoreExecutors.directExecutor()
        )
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        connectPlayerService(activity, owner.lifecycleScope)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        disconnectPlayerService()
    }
}
