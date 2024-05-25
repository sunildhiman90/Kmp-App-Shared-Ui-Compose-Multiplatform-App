import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.CanvasBasedWindow
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.defaultImageResultMemoryCache
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import com.seiko.imageloader.intercept.imageMemoryCacheConfig
import com.seiko.imageloader.intercept.painterMemoryCacheConfig
import di.startKoinJs
import okio.FileSystem
import org.jetbrains.skiko.wasm.onWasmReady
import root.RootComponent
import root.RootContent
import scrollbar.MainVerticalLazyGridScrollbar
import scrollbar.MainVerticalLazyListScrollbar
import scrollbar.MainVerticalScrollbar


val koin = startKoinJs().koin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    onWasmReady {
        CanvasBasedWindow("KmpApp2") {
            CompositionLocalProvider(
                LocalImageLoader provides remember { generateImageLoader() },
            ) {
                val root = koin.get<RootComponent>()
                RootContent(
                    root,
                    modifier = Modifier,
                    lazyGridScrollBar = { scrollState, modifier ->
                        MainVerticalLazyGridScrollbar(scrollState, modifier)
                    },
                    lazyListScrollBar = { scrollState, modifier ->
                        MainVerticalLazyListScrollbar(scrollState, modifier)
                    },
                    scrollBar = { scrollState, modifier ->
                        MainVerticalScrollbar(scrollState, modifier)
                    },
                )
            }
        }
    }
}

fun generateImageLoader(): ImageLoader {
    return ImageLoader {
        components {
            setupDefaultComponents()
        }
        interceptor {

            // cache 32MB bitmap
            bitmapMemoryCacheConfig {
                maxSize(32 * 1024 * 1024) // 32MB
            }
            // cache 50 image
            imageMemoryCacheConfig {
                maxSize(50)
            }
            // cache 50 painter
            painterMemoryCacheConfig {
                maxSize(50)
            }
            diskCacheConfig {
                directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY)
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }
}
