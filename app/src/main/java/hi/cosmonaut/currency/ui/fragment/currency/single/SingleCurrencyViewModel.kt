package hi.cosmonaut.currency.ui.fragment.currency.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import hi.cosmonaut.base.data.wrapper.Result
import hi.cosmonaut.base.ui.viewmodel.feature.FeatureViewModel
import hi.cosmonaut.currency.data.model.request.currency.convert.ConvertCurrency
import hi.cosmonaut.currency.data.source.remote.NetworkManager
import hi.cosmonaut.currency.data.source.remote.contract.NetworkContract
import hi.cosmonaut.currency.domain.usecase.currency.convert.ConvertCurrencyUseCase
import kotlinx.coroutines.launch

class SingleCurrencyViewModel: FeatureViewModel() {

    private val network: NetworkContract.ICurrency = NetworkManager()

    private val _conversionData: MutableLiveData<String> = MutableLiveData<String>()
    val conversionData: LiveData<String> get() = _conversionData

    fun navigateToHome() = navigate(SingleCurrencyFragmentDirections.actionCurrencyToHome())

    fun convert(text: String?) {
        text?.let{

            val convertCurrency = ConvertCurrency("USD", "EUR", text)

            val convertCurrencyUseCase = ConvertCurrencyUseCase(
                network,
                convertCurrency
            )

            viewModelScope.launch {
                when(val result = convertCurrencyUseCase()){
                    is Result.Success<*> -> handleAsConvertCurrency(result.data as String)
                    is Result.Error<*> -> sendMessage(result.messageId)
                }
            }
        }
    }

    private fun handleAsConvertCurrency(result: String) {
        _conversionData.postValue(result)
    }

}