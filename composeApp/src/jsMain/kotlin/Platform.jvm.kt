class JSPlatform: Platform {
    override val name: String = "Compose for Web using Kotlin/JS "
}

actual fun getPlatform(): Platform = JSPlatform()