package database.datasource

import data.Product
import database.Database
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ProductsRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getAllProducts(): List<Product> {
        val response = httpClient.get("https://fakestoreapi.com/products")
        return response.body()
    }

}