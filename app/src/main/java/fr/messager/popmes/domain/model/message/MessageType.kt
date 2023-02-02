package fr.messager.popmes.domain.model.message

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

sealed interface MessageType {

    object Camera : MessageType {
        @Composable
        override fun DrawContent(modifier: Modifier, shortVersion: Boolean) {
            TODO("Not yet implemented")
        }
    }

    object Photo : MessageType {
        @Composable
        override fun DrawContent(modifier: Modifier, shortVersion: Boolean) {
            TODO("Not yet implemented")
        }
    }

    object File : MessageType {
        @Composable
        override fun DrawContent(modifier: Modifier, shortVersion: Boolean) {
            TODO("Not yet implemented")
        }
    }

    object Vocal : MessageType {
        @Composable
        override fun DrawContent(modifier: Modifier, shortVersion: Boolean) {
            TODO("Not yet implemented")
        }
    }

    data class MessageData(
        val text: String,
    ) : MessageType {
        @Composable
        override fun DrawContent(
            modifier: Modifier,
            shortVersion: Boolean
        ) {
            val maxLines by remember(shortVersion) {
                derivedStateOf {
                    if (shortVersion) 2 else Int.MAX_VALUE
                }
            }

            val overflow by remember(shortVersion) {
                derivedStateOf {
                    if (shortVersion) TextOverflow.Ellipsis else TextOverflow.Clip
                }
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = maxLines,
                modifier = modifier,
                overflow = overflow,
            )
        }
    }

    object Survey : MessageType {
        @Composable
        override fun DrawContent(modifier: Modifier, shortVersion: Boolean) {
            TODO("Not yet implemented")
        }
    }

    object GIF : MessageType {
        @Composable
        override fun DrawContent(modifier: Modifier, shortVersion: Boolean) {
            TODO("Not yet implemented")
        }
    }

    @Composable
    fun DrawContent(
        modifier: Modifier,
        shortVersion: Boolean
    )
}
