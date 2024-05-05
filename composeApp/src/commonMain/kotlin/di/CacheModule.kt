package di

import database.Database
import database.DbHelper
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

fun cacheModule() = module {

    single<CoroutineContext> { Dispatchers.Default }
    single { CoroutineScope(get()) }

    single { DbHelper(get()) }
    single { Database(get(), get()) }
}