package root

import NavigationType
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import detail.DetailContent
import list.ListContent

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RootContent(
    component: RootComponent,
    lazyListScrollBar: (@Composable (LazyListState, Modifier) -> Unit)? = null,
    lazyGridScrollBar: (@Composable (LazyGridState, Modifier) -> Unit)? = null,
    scrollBar: (@Composable (ScrollState, Modifier) -> Unit)? = null,
    modifier: Modifier = Modifier
) {

    val windowSizeClass = calculateWindowSizeClass()
    val navigationType = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> NavigationType.BOTTOM_NAV
        WindowWidthSizeClass.Medium -> NavigationType.NAV_RAIL
        WindowWidthSizeClass.Expanded -> NavigationType.PERMANENT_NAV_DRAWER
        else -> NavigationType.BOTTOM_NAV
    }

    AppContentList(component, navigationType, lazyListScrollBar, lazyGridScrollBar, scrollBar, modifier)

}

@Composable
fun AppContentList(
    component: RootComponent,
    navigationType: NavigationType,
    lazyListScrollBar: (@Composable (LazyListState, Modifier) -> Unit)? = null,
    lazyGridScrollBar: (@Composable (LazyGridState, Modifier) -> Unit)? = null,
    scrollBar: (@Composable (ScrollState, Modifier) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    MaterialTheme {
        Children(
            stack = component.stack, modifier = modifier, animation = stackAnimation(fade())
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.ListChild -> ListContent(
                    child.component,
                    navigationType,
                    lazyListScrollBar,
                    lazyGridScrollBar,
                    scrollBar,
                    modifier
                )

                is RootComponent.Child.DetailChild -> DetailContent(child.component)
            }
        }
    }
}

