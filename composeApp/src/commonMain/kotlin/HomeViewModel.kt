import data.Product
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(listOf())
    val products = _products.asStateFlow()

    private val homeRepository = HomeRepository()

    init {
        viewModelScope.launch {
            homeRepository.getProducts().collect { products ->
                _products.update { it + products }
            }
        }
    }

}