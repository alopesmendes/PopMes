package fr.messager.popmes.presentation.components.dimensions

import android.app.Activity
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun PopMesWindowSize(
    activity: Activity,
    windowSizeDimension: WindowSizeDimension,
    compact: @Composable () -> Unit,
    medium: @Composable () -> Unit = compact,
    expanded: @Composable () -> Unit = medium,
) {
    val windowSizeClass = calculateWindowSizeClass(activity)
    when(windowSizeDimension) {
        is WindowSizeDimension.Width -> {
            when(windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> compact()
                WindowWidthSizeClass.Medium -> medium()
                WindowWidthSizeClass.Expanded -> expanded()
            }
        }
        is WindowSizeDimension.Height -> {
            when(windowSizeClass.heightSizeClass) {
                WindowHeightSizeClass.Compact -> compact()
                WindowHeightSizeClass.Medium -> medium()
                WindowHeightSizeClass.Expanded -> expanded()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PopMesWindowSizePreview() {
    val activity = LocalContext.current as Activity
    PopMesWindowSize(
        activity = activity, 
        windowSizeDimension = WindowSizeDimension.Width,
        compact = {
            Text(text = "Compact")
        },
        medium = {
            Text(text = "Medium")
        },
        expanded = {
            Text(text = "Expanded")
        }
    )
}