package di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.koin.dsl.module

val jvmModule = module {
    single { LifecycleRegistry() }
    single<ComponentContext> { DefaultComponentContext(lifecycle = get<LifecycleRegistry>()) }
}
fun startKoinJvm() = initKoin(additionalModules = listOf(jvmModule))