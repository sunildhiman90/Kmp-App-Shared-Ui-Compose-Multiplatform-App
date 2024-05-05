import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import data.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import list.ListComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(
    products: State<ListComponent.Model>,
    navigationType: NavigationType,
    lazyListScrollBar: (@Composable (LazyListState, Modifier) -> Unit)? = null,
    lazyGridScrollBar: (@Composable (LazyGridState, Modifier) -> Unit)? = null,
    scrollBar: (@Composable (ScrollState, Modifier) -> Unit)? = null,
    onItemClicked: (Product) -> Unit
) {

    val scrollState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    var cols = 2
    if (navigationType == NavigationType.NAV_RAIL) {
        cols = 3
    }
    if (navigationType == NavigationType.PERMANENT_NAV_DRAWER) {
        cols = 4
    }

    val showScrollBars =
        (getPlatform().name == PlatformType.JS.name || getPlatform().name == PlatformType.DESKTOP.name) && navigationType != NavigationType.BOTTOM_NAV

    if (!showScrollBars) {
        //for mobile
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            ListUi(cols, scrollState, coroutineScope, products, onItemClicked)

        }
    } else {
        //for web and desktop
        Box(modifier = Modifier.fillMaxSize()) {

            ListUi(cols, scrollState, coroutineScope, products, onItemClicked)

            if (lazyGridScrollBar != null) {
                lazyGridScrollBar(scrollState, Modifier.align(Alignment.CenterEnd))
            }
        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListUi(
    cols: Int,
    scrollState: LazyGridState,
    coroutineScope: CoroutineScope,
    products: State<ListComponent.Model>,
    onItemClicked: (Product) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(cols),
        state = scrollState,
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                coroutineScope.launch {
                    scrollState.scrollBy(-delta)
                }
            })
    ) {

        item(span = { GridItemSpan(cols) }) {
            Column {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    query = "",
                    active = false,
                    onActiveChange = {},
                    onQueryChange = {},
                    onSearch = {},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    placeholder = { Text("Search Products") }
                ) {}
                Spacer(modifier = Modifier.height(16.dp))
            }

        }


        items(
            items = products.value.items,
            key = { product -> product.id.toString() }) { product ->

            Card(
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.padding(8.dp).fillMaxWidth().clickable {
                    onItemClicked(product)
                },
                elevation = 2.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val painter = rememberImagePainter(url = product.image.toString())
                    Image(
                        painter,
                        modifier = Modifier.height(130.dp).padding(8.dp),
                        contentDescription = product.title
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            product.title.toString(),
                            textAlign = TextAlign.Start,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 16.dp)
                                .heightIn(min = 40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            "${product.price.toString()} INR ",
                            textAlign = TextAlign.Start,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.wrapContentWidth()
                                .padding(horizontal = 16.dp).heightIn(min = 40.dp)
                        )
                    }
                }
            }
        }
    }
}