package database.datasource

import data.Product
import database.Database

class ProductsLocalDataSource(
    private val database: Database
) {

    suspend fun getAllProducts(): List<Product> {
        println("database_getAllProducts")
        return database.getAllProducts()
    }

    suspend fun clearDb() {
        return database.clearDatabase()
    }


    suspend fun saveProducts(items: List<Product>) {
        return database.createProducts(items)
    }
}