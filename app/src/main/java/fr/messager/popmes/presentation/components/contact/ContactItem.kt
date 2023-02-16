package fr.messager.popmes.presentation.components.contact

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import fr.messager.popmes.presentation.components.text.TextAndLabel

@Composable
fun ContactItem(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    icon: Painter,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val alpha by remember(enabled) {
        derivedStateOf {
            if (enabled) 1.0f else .5f
        }
    }
    Row(
        modifier = if (enabled)
            modifier.clickable { onClick() }
        else
            modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileImage(
            painter = icon,
            description = "contact profile image",
            modifier = Modifier.padding(end = 16.dp),
            alpha = alpha,
        )

        TextAndLabel(
            name = name,
            description = description
        )
    }
}