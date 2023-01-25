package fr.messager.popmes.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import fr.messager.popmes.R


sealed class NavItem(
    @DrawableRes private val imageId: Int? = null,
    private val imageVector: ImageVector? = null,
    private val imageBitmap: ImageBitmap? = null,
    @StringRes val titleId: Int,
    val screen: Screen,
    val contentDescription: String? = null,
) {

    @Throws(IllegalArgumentException::class)
    @Composable
    fun painterIcon(): Painter {
        return if (imageId != null)
            painterResource(imageId)
        else if (imageVector != null)
            rememberVectorPainter(imageVector)
        else if (imageBitmap != null)
            BitmapPainter(imageBitmap)
        else
            throw IllegalArgumentException("All fields are null in NavItem")
    }

    object Home: NavItem(
        titleId = R.string.title_home,
        imageVector = Icons.Filled.Home,
        screen = Screen.Home,
        contentDescription = "home nav item",
    )

    object Contacts: NavItem(
        titleId = R.string.title_contacts,
        imageId = R.drawable.ic_contacts,
        screen = Screen.Contacts,
        contentDescription = "contacts nav item",
    )

    object Tasks: NavItem(
        titleId = R.string.title_tasks,
        imageId = R.drawable.ic_task,
        screen = Screen.Tasks,
        contentDescription = "tasks nav item",
    )
}
