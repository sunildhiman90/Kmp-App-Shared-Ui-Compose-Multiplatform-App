package list

import AppContent
import NavigationType
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState


@Composable
fun ListContent(
    component: ListComponent,
    navigationType: NavigationType,
    lazyListScrollBar: (@Composable (LazyListState, Modifier) -> Unit)? = null,
    lazyGridScrollBar: (@Composable (LazyGridState, Modifier) -> Unit)? = null,
    scrollBar: (@Composable (ScrollState, Modifier) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val products = component.model.subscribeAsState()

    AppContent(products, navigationType, lazyListScrollBar, lazyGridScrollBar, scrollBar) {
        component.onItemClicked(it)
    }

}