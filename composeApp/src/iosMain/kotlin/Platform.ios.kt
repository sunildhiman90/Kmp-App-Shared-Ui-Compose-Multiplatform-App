import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = PlatformType.IOS.name
}

actual fun getPlatform(): Platform = IOSPlatform()