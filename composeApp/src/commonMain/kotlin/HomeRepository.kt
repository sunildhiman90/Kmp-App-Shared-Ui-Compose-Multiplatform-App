import apiClient.httpClient
import data.Product
import database.datasource.ProductsLocalDataSource
import database.datasource.ProductsRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.flow

class HomeRepository(
    private val productsLocalDataSource: ProductsLocalDataSource,
    private val productsRemoteDataSource: ProductsRemoteDataSource
) {

    private suspend fun getAllProducts(forceReload: Boolean = false): List<Product> {
        val cachedItems = productsLocalDataSource.getAllProducts()
        return if(cachedItems.isNotEmpty() && !forceReload) {
            println("fromCache")
            cachedItems
        } else {
            println("fromNetwork")
            productsRemoteDataSource.getAllProducts().also {
                productsLocalDataSource.clearDb()
                productsLocalDataSource.saveProducts(it)
            }
        }
    }

    fun getProducts(forceReload: Boolean = false) = flow {
        emit(getAllProducts(forceReload))
    }
}