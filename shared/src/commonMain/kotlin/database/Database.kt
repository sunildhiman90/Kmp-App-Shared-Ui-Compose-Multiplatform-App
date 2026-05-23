package database

import app.cash.sqldelight.async.coroutines.awaitAsList
import data.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Database(private val dbHelper: DbHelper, private val scope: CoroutineScope) {


    fun clearDatabase() {
        scope.launch {
            dbHelper.withDatabase { database ->
                database.appDatabaseQueries.removeAllProducts()
            }
        }
    }


    suspend fun getAllProducts(): List<Product> {
        var items: List<Product>
        val result = scope.async {
            dbHelper.withDatabase { database ->
                items = database.appDatabaseQueries.selectAllProducts(::mapProductSelecting)
                    .awaitAsList()
                items
            }
        }

        return result.await()
    }

    private fun mapProductSelecting(
        id: Long,
        title: String,
        image: String,
        price: Double,
        category: String?,
        description: String?,
    ): Product {

        return Product(
            id = id.toInt(),
            price = price,
            category = category,
            description = description,
            title = title,
            image = image
        )
    }

    suspend fun createProducts(items: List<Product>) {
        val result = scope.async {
            dbHelper.withDatabase { database ->
               items.forEach {
                   insertProduct(it)
               }
            }
        }
    }

    private suspend fun insertProduct(item: Product) {
       scope.async {
            dbHelper.withDatabase { database ->
                println("insertProduct_item=$item")
                database.appDatabaseQueries.insertProduct(
                    id = item.id?.toLong(),
                    title = item.title.toString(),
                    image = item.image.toString(),
                    price = item.price ?: 0.0,
                    category = item.category.toString(),
                    description = item.description.toString(),
                )
            }
        }
    }
}