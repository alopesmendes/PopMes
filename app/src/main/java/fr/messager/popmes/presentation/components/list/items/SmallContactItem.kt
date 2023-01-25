package fr.messager.popmes.presentation.components.list.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.messager.popmes.R
import fr.messager.popmes.presentation.components.image.ProfileImage

@Composable
fun SmallContactItem(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    icon: Painter,
    onClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable { onClick() },
    ) {
        ProfileImage(
            painter = icon,
            description = description,
        )

        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(40.dp),
            maxLines = 1,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SmallContactItemPreview() {
    SmallContactItem(
        name = "Ailton",
        description = "",
        icon = painterResource(id = R.drawable.avatar_0)
    ) {

    }
}