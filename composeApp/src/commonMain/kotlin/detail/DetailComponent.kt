package detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import data.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface DetailComponent {
    val model: Value<Model>

    fun onBackPressed()

    data class Model(
        val item: Product
    )
}

class DefaultDetailComponent(
    private val componentContext: ComponentContext,
    private val item: Product,
    private val onBack: () -> Unit,
) : DetailComponent, ComponentContext by componentContext {

    private val _model = MutableValue(DetailComponent.Model(item = item))
    override val model: Value<DetailComponent.Model> = _model
    override fun onBackPressed() {
        onBack()
    }

    init {
        CoroutineScope(Dispatchers.Default).launch {
           //IF we need to call api in Detail Component, call here
        }
    }


}