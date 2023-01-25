package fr.messager.popmes.presentation.components.list.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import fr.messager.popmes.presentation.components.image.ProfileImage
import fr.messager.popmes.presentation.components.text.TextAndLabel

@Composable
fun ContactItem(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    icon: Painter,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileImage(
            painter = icon,
            description = "contact profile image",
            modifier = Modifier.padding(end = 16.dp)
        )

        TextAndLabel(
            name = name,
            description = description
        )
    }
}