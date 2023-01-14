package fr.messager.popmes.presentation.components.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import fr.messager.popmes.presentation.components.state.rememberTimeAgo
import java.time.Instant

@Composable
fun FullNameAndDateText(
    modifier: Modifier,
    name: String,
    date: Instant,
    color: Color = Color.Unspecified,
) {
    val timeAgo = rememberTimeAgo(date = date)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            color = color,
        )

        Text(
            text = timeAgo.value,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
        )

    }
}