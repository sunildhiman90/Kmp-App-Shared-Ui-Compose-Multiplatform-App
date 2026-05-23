package database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.codingambitions.kmpapp2.AppDatabase


actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(
            url = "jdbc:sqlite:${System.getProperty("user.dir")}/kmpapp2.db"
        )
        AppDatabase.Schema.create(driver)
        return driver
    }
}
