package fr.messager.popmes.presentation.components.card

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.messager.popmes.common.Extension.dateAgo
import fr.messager.popmes.common.Extension.displayDate
import fr.messager.popmes.domain.model.task.Task
import fr.messager.popmes.domain.model.task.TaskPriority
import fr.messager.popmes.domain.model.task.TaskType
import fr.messager.popmes.presentation.components.text.TextAndLabel
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.elevatedShape,
    colors: CardColors = CardDefaults.elevatedCardColors(),
    elevation: CardElevation = CardDefaults.elevatedCardElevation(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    task: Task,
) {
    val duration by remember(task.beginDate, task.endDate) {
        derivedStateOf {
            task.endDate?.let { task.beginDate.dateAgo(it) } ?: task.beginDate.displayDate()
        }
    }
    ElevatedCard(
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = elevation,
        onClick = onClick,
        interactionSource = interactionSource,
        enabled = enabled,
    ) {

//        Image(
//            painter = painterResource(id = R.drawable.ic_schedule),
//            contentDescription = null,
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(MaterialTheme.colorScheme.primaryContainer),
//            contentScale = ContentScale.Crop,
//        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.displaySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                TextAndLabel(
                    name = task.priority.name,
                    description = task.type::class.simpleName ?: "",
                )
            }
            Text(
                text = duration,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TakCardPreview() {
    TaskCard(
        onClick = {  },
        task = Task(
            id = "0",
            title = "One Piece",
            type = TaskType.Task,
            priority = TaskPriority.LOW,
            beginDate = Instant.now(),
            endDate = null,
            description = "One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
        )
    )
}