import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import androidx.compose.ui.window.Window
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.defaultImageResultMemoryCache
import okio.FileSystem
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    onWasmReady {
        CanvasBasedWindow("KmmApp2") {
            CompositionLocalProvider(
                LocalImageLoader provides remember { generateImageLoader() },
            ) {
                App()
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
            // cache 100 success image result, without bitmap
            defaultImageResultMemoryCache()
            memoryCacheConfig {
                maxSizeBytes(32 * 1024 * 1024) // 32MB
            }
            diskCacheConfig {
                directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY)
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }
}
