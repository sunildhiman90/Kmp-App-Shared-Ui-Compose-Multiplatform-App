package list

import AppContent
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState


@Composable
fun ListContent(
    component: ListComponent
) {
    val products = component.model.subscribeAsState()

    AppContent(products) {
        component.onItemClicked(it)
    }

}