package fr.messager.popmes.presentation.components.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import fr.messager.popmes.common.Extension.dateAgo
import kotlinx.coroutines.delay
import java.time.Instant

@Composable
fun rememberTimeAgo(
    date: Instant,
): State<String> {
    return produceState(initialValue = "") {
        while (true) {
            value = date.dateAgo(Instant.now())
            delay(1000L)
        }
    }
}