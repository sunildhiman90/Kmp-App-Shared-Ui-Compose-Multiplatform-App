import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = PlatformType.ANDROID.name
}

actual fun getPlatform(): Platform = AndroidPlatform()