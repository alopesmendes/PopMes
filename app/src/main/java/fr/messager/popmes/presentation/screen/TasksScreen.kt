package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import fr.messager.popmes.domain.model.task.Task
import fr.messager.popmes.domain.model.task.TaskPriority
import fr.messager.popmes.domain.model.task.TaskType
import fr.messager.popmes.presentation.components.dimensions.PopMesWindowSize
import fr.messager.popmes.presentation.components.dimensions.WindowSizeDimension
import fr.messager.popmes.presentation.components.views.TaskComponent
import fr.messager.popmes.presentation.navigation.NavItem
import kotlinx.coroutines.CoroutineScope
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    activity: Activity,
    navItems: List<NavItem>,
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
) {
    BackHandler(onBack = onBack)

    PopMesWindowSize(
        activity = activity,
        windowSizeDimension = WindowSizeDimension.Width,
        compact = {
            TaskComponent(
                activity = activity,
                navItems = navItems,
                drawerState = drawerState,
                scope = scope,
                selectedItem = selectedItem,
                onSelectedItemChange = onSelectedItemChange,
                onNavigate = onNavigate,
                onAddNewTask = { /*TODO*/ },
                onAddNewSchedule = { /*TODO*/ },
                tasks = listOf(
                    Task(
                        id = "0",
                        title = "One Piece",
                        beginDate = Instant.now(),
                        endDate = null,
                        priority = TaskPriority.LOW,
                        type = TaskType.Alarm,
                        description = "One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
                    ),
                    Task(
                        id = "1",
                        title = "One Piece",
                        beginDate = Instant.now(),
                        endDate = null,
                        priority = TaskPriority.LOW,
                        type = TaskType.Alarm,
                        description = "One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
                    ),
                    Task(
                        id = "2",
                        title = "One Piece",
                        beginDate = Instant.now(),
                        endDate = null,
                        priority = TaskPriority.LOW,
                        type = TaskType.Alarm,
                        description = "One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
                    ),
                    Task(
                        id = "3",
                        title = "One Piece",
                        beginDate = Instant.now(),
                        endDate = null,
                        priority = TaskPriority.LOW,
                        type = TaskType.Alarm,
                        description = "One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
                    ),
                    Task(
                        id = "4",
                        title = "One Piece",
                        beginDate = Instant.now(),
                        endDate = null,
                        priority = TaskPriority.LOW,
                        type = TaskType.Alarm,
                        description = "One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
                    )
                )
            )
        }
    )
}