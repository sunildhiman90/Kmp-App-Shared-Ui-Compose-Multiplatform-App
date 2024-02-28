class JVMPlatform: Platform {
    override val name: String = PlatformType.DESKTOP.name
}

actual fun getPlatform(): Platform = JVMPlatform()