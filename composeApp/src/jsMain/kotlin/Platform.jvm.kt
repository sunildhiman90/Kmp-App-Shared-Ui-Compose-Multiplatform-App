class JSPlatform: Platform {
    override val name: String = PlatformType.JS.name
}

actual fun getPlatform(): Platform = JSPlatform()