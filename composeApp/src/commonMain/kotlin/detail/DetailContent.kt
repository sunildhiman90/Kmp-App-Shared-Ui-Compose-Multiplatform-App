package detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.P
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    component: DetailComponent
) {

    val product = component.model.subscribeAsState()

    Scaffold(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                modifier = Modifier.windowInsetsPadding(WindowInsets(0.dp)),
                title = { Text("Product Detail") },
                navigationIcon = {
                    IconButton(onClick = {
                        component.onBackPressed()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Go Back")
                    }
                }
            )
        }
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(16.dp)
        ) {
            Text("Product title: ${product.value.item.title}")
            Text("Product Desc: ${product.value.item.description}")
        }
    }
}