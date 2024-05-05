package scrollbar

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainVerticalLazyListScrollbar(scrollState: LazyListState, modifier: Modifier) {
    VerticalScrollbar(
        modifier = modifier.padding(start = 3.dp, end = 3.dp),
        adapter = rememberScrollbarAdapter(scrollState),
        style = LocalScrollbarStyle.current
    )
}

@Composable
fun MainVerticalLazyGridScrollbar(scrollState: LazyGridState, modifier: Modifier) {
    VerticalScrollbar(
        modifier = modifier.padding(start = 3.dp, end = 3.dp),
        adapter = rememberScrollbarAdapter(scrollState),
        style = LocalScrollbarStyle.current
    )
}

@Composable
fun MainVerticalScrollbar(scrollState: ScrollState, modifier: Modifier) {
    VerticalScrollbar(
        modifier = modifier.padding(start = 3.dp, end = 3.dp),
        adapter = rememberScrollbarAdapter(scrollState),
        style = LocalScrollbarStyle.current
    )
}