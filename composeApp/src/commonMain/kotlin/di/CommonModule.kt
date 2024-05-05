package di

import HomeRepository
import HomeViewModel
import org.koin.dsl.module
import root.DefaultRootComponent
import root.RootComponent

fun commonModule() = networkModule() + module {

    single {
        HomeRepository(get())
    }

    single {
        HomeViewModel(get())
    }

    single<RootComponent> {
        DefaultRootComponent(
            componentContext = get(),
            homeViewModel = get()
        )
    }

}