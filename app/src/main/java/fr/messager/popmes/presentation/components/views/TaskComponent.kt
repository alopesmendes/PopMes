package fr.messager.popmes.presentation.components.views

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.messager.popmes.R
import fr.messager.popmes.domain.model.task.Task
import fr.messager.popmes.domain.model.task.TaskPriority
import fr.messager.popmes.domain.model.task.TaskType
import fr.messager.popmes.presentation.components.buttons.PopMesTextButton
import fr.messager.popmes.presentation.components.card.TaskCard
import fr.messager.popmes.presentation.components.list.PopMesListColumn
import fr.messager.popmes.presentation.components.navigation.Navigation
import fr.messager.popmes.presentation.navigation.NavItem
import kotlinx.coroutines.CoroutineScope
import java.time.Instant

@Composable
fun TaskComponent(
    modifier: Modifier = Modifier,
    onAddNewTask: () -> Unit,
    onAddNewSchedule: () -> Unit,
    tasks: List<Task>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        PopMesTextButton(
            onClick = onAddNewTask,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        ) {
            Icon(
                painterResource(id = R.drawable.ic_add_task),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(id = R.string.title_new_task))
        }

        PopMesTextButton(
            onClick = onAddNewSchedule,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        ) {
            Icon(
                painterResource(id = R.drawable.ic_schedule),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(id = R.string.title_new_schedule))
        }

        Text(
            text = stringResource(id = R.string.tasks_on_pop_mes),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.outline,
        )

        PopMesListColumn(
            values = tasks,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) { index, value ->
            TaskCard(
                onClick = { /*TODO*/ },
                task = value,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskComponent(
    modifier: Modifier = Modifier,
    activity: Activity,
    navItems: List<NavItem>,
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    onAddNewTask: () -> Unit,
    onAddNewSchedule: () -> Unit,
    tasks: List<Task>,
) {
    Navigation(
        activity = activity,
        navItems = navItems,
        onNavigate = onNavigate,
        drawerState = drawerState,
        scope = scope,
        selectedItem = selectedItem,
        onSelectedItemChange = onSelectedItemChange,
        modifier = modifier,
    ) {
        TaskComponent(
            modifier = modifier,
            onAddNewTask = onAddNewTask,
            onAddNewSchedule = onAddNewSchedule,
            tasks = tasks,
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun TaskComponentPreview() {
    TaskComponent(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onAddNewTask = {  },
        onAddNewSchedule = {  },
        tasks = listOf(
            Task(
                id = "0",
                title = "One Piece",
                beginDate = Instant.now(),
                endDate = null,
                priority = TaskPriority.LOW,
                type = TaskType.Task,
                description = "One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
            ),
            Task(
                id = "1",
                title = "One Piece",
                beginDate = Instant.now(),
                endDate = null,
                priority = TaskPriority.LOW,
                type = TaskType.Task,
                description = "One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
            ),
            Task(
                id = "2",
                title = "One Piece",
                beginDate = Instant.now(),
                endDate = null,
                priority = TaskPriority.LOW,
                type = TaskType.Task,
                description = "One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
            ),
            Task(
                id = "3",
                title = "One Piece",
                beginDate = Instant.now(),
                endDate = null,
                priority = TaskPriority.LOW,
                type = TaskType.Task,
                description = "One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
            ),
            Task(
                id = "4",
                title = "One Piece",
                beginDate = Instant.now(),
                endDate = null,
                priority = TaskPriority.LOW,
                type = TaskType.Task,
                description = "One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
            )
        )
    )
}