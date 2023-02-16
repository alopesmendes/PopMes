package fr.messager.popmes.presentation.components.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    description: String,
    alpha: Float = 1f,
) {
    Image(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .alpha(alpha),
        painter = painter,
        contentDescription = description,
    )
}