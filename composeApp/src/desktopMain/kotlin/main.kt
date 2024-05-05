import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.defaultImageResultMemoryCache
import di.startKoinJvm
import okio.Path.Companion.toOkioPath
import root.DefaultRootComponent
import root.RootComponent
import root.RootContent
import scrollbar.MainVerticalLazyGridScrollbar
import scrollbar.MainVerticalLazyListScrollbar
import scrollbar.MainVerticalScrollbar
import java.io.File


val koin = startKoinJvm().koin

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "KmpApp2") {
        val rootComponent = koin.get<RootComponent>()
        RootContent(
            rootComponent, modifier = Modifier,
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
                directory(getCacheDir().toOkioPath().resolve("image_cache"))
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }
}

enum class OperatingSystem {
    Windows, Linux, MacOS, Unknown
}

private val currentOperatingSystem: OperatingSystem
    get() {
        val operSys = System.getProperty("os.name").lowercase()
        return if (operSys.contains("win")) {
            OperatingSystem.Windows
        } else if (operSys.contains("nix") || operSys.contains("nux") ||
            operSys.contains("aix")
        ) {
            OperatingSystem.Linux
        } else if (operSys.contains("mac")) {
            OperatingSystem.MacOS
        } else {
            OperatingSystem.Unknown
        }
    }

// about currentOperatingSystem, see app
private fun getCacheDir() = when (currentOperatingSystem) {
    OperatingSystem.Windows -> File(System.getenv("AppData"), "$ApplicationName/cache")
    OperatingSystem.Linux -> File(System.getProperty("user.home"), ".cache/$ApplicationName")
    OperatingSystem.MacOS -> File(
        System.getProperty("user.home"),
        "Library/Caches/$ApplicationName"
    )

    else -> throw IllegalStateException("Unsupported operating system")
}

private const val ApplicationName = "KmmAppExample2"

@Preview
@Composable
fun AppDesktopPreview() {
    //App()
}