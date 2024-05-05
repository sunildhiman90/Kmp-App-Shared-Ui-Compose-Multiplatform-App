package database

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.codingambitions.kmpapp2.AppDatabase

actual class DriverFactory(val context: Context) {
    actual suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema.synchronous(), context, "app.db")
    }
}