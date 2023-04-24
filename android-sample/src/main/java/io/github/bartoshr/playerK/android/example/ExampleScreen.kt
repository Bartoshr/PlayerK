package io.github.bartoshr.playerK.android.example

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward5
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay5
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import io.github.bartoshr.playerK.PlayerStatus

@Composable
fun ExampleScreen(viewModel: ExampleViewModel = viewModel()) {

    val state by viewModel.state.collectAsState(initial = PlayerUiState())

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Song Title
        Text(
            text = state.title,
            style = MaterialTheme.typography.h5
        )

        // Artist Name
        Text(
            text = state.artist,
            style = MaterialTheme.typography.subtitle1
        )

        // Album Art
        CoilImage(
            imageModel = { state.coverUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = Modifier
                .aspectRatio(1 / 1f)
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Current Time
            Text(
                text = state.currentTime,
                style = MaterialTheme.typography.h5
            )

            // Total Time
            Text(
                text = state.totalTime,
                style = MaterialTheme.typography.h5
            )
        }

        // Playback controls
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = viewModel::rewind
            ) {
                PlayerIcon(Icons.Default.Replay5, size = 48.dp)
            }

            IconButton(
                onClick = viewModel::togglePlay
            ) {
                when (state.status) {
                    PlayerStatus.Playing -> PlayerIcon(Icons.Default.Pause)
                    PlayerStatus.Idle -> PlayerIcon(Icons.Default.PlayArrow)
                    PlayerStatus.Loading -> ProgressIcon()
                }
            }

            IconButton(
                onClick = viewModel::forward
            ) {
                PlayerIcon(Icons.Default.Forward5, size = 48.dp)
            }
        }
    }
}

@Composable
fun ProgressIcon() {
    CircularProgressIndicator(
        modifier = Modifier
            .size(80.dp)
            .padding(16.dp),
        color = LocalContentColor.current,
    )
}

@Composable
fun PlayerIcon(imageVector: ImageVector, size: Dp = 96.dp) {
    Icon(
        imageVector,
        contentDescription = null,
        modifier = Modifier.size(size)
    )
}
