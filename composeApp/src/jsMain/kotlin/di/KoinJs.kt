package di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import database.DriverFactory
import org.koin.dsl.module


val jsModule = module {
    single { LifecycleRegistry() }
    single<ComponentContext> { DefaultComponentContext(lifecycle = get<LifecycleRegistry>()) }
    single { DriverFactory() }

}
fun startKoinJs() = initKoin(additionalModules = listOf(jsModule))