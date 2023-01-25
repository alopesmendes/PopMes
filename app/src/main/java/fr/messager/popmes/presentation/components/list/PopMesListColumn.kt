package fr.messager.popmes.presentation.components.list

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> PopMesListColumn(
    modifier: Modifier = Modifier,
    values: List<T>,
    key: ((Int) -> Any)? = null,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    contentType: (Int) -> Any? = { null },
    onItemContent: @Composable() (LazyItemScope.(index: Int, value: T) -> Unit),
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
        verticalArrangement = verticalArrangement,
    ) {
        items(
            count = values.size,
            key = key,
            contentType = contentType,
        ) { index ->
            onItemContent(index, values[index])
            if (index < values.lastIndex)
                Spacer(modifier = Modifier.height(8.dp))
            else
                Spacer(modifier = Modifier.height(64.dp))
        }
    }
}