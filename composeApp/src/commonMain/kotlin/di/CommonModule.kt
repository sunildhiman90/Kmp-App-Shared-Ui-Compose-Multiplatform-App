package di

import HomeRepository
import HomeViewModel
import database.datasource.ProductsLocalDataSource
import database.datasource.ProductsRemoteDataSource
import org.koin.dsl.module
import root.DefaultRootComponent
import root.RootComponent

fun commonModule() = cacheModule() + networkModule() + module {

    single {
        ProductsLocalDataSource(get())
    }

    single {
        ProductsRemoteDataSource(get())
    }

    single {
        HomeRepository(get(), get())
    }

    single {
        HomeViewModel(get())
    }

    single<RootComponent> {
        DefaultRootComponent(
            componentContext = get(),
            homeRepository = get()
        )
    }

}