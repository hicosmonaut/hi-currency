package hi.cosmonaut.currency.ui.fragment.currency.my

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import hi.cosmonaut.base.data.wrapper.Result
import hi.cosmonaut.base.ui.viewmodel.feature.FeatureViewModel
import hi.cosmonaut.currency.data.model.view.Currency
import hi.cosmonaut.currency.data.source.local.contract.DatabaseContract
import hi.cosmonaut.currency.data.source.local.database.DatabaseManager
import hi.cosmonaut.currency.domain.usecase.currency.my.delete.DeleteFromMyCurrenciesUseCase
import hi.cosmonaut.currency.domain.usecase.currency.my.local.GetMyCurrenciesUseCase
import hi.cosmonaut.currency.ui.fragment.home.HomeFragmentDirections
import kotlinx.coroutines.launch

class MyCurrenciesViewModel: FeatureViewModel(){

    private val _adapterData: MutableLiveData<List<Currency>> = MutableLiveData<List<Currency>>()
    val adapterData: LiveData<List<Currency>> get() = _adapterData

    private val _navigationHomeData: MutableLiveData<NavDirections> = MutableLiveData<NavDirections>()
    val navigationHomeData: LiveData<NavDirections> get() = _navigationHomeData




    fun fetchData(context: Context){
        val local = DatabaseManager(context)
        val getMyCurrenciesViewModel = GetMyCurrenciesUseCase(local)

        viewModelScope.launch {
            when(val result = getMyCurrenciesViewModel()){
                is Result.Success<*> -> handleAsMyCurrencies(result.data as List<*>)
                is Result.Error<*> -> sendMessage(result.messageId)
            }
        }
    }

    private fun navigateHome(action: NavDirections) = _navigationHomeData.postValue(action)

    private fun handleAsMyCurrencies(list: List<*>) {
        val currencies = list.filterIsInstance(Currency::class.java)
        sendDataToAdapter(currencies)
    }

    private fun sendDataToAdapter(currencies: List<Currency>) = _adapterData.postValue(currencies)

    fun navigateToSelectedCurrency() = navigateHome(HomeFragmentDirections.actionHomeToCurrency())

    fun deleteFromFavoritesAndFetch(context: Context, currency: Currency) {
        val local = DatabaseManager(context)
        val deleteFromMyCurrenciesUseCase = DeleteFromMyCurrenciesUseCase(local, currency)

        viewModelScope.launch {
            when(val result = deleteFromMyCurrenciesUseCase()){
                is Result.Success<*> -> handleAsDelete(context, result.data as Currency)
                is Result.Error<*> -> sendMessage(result.messageId)
            }
        }
    }

    private fun handleAsDelete(context: Context, currency: Currency) {
        fetchData(context)
        sendMessage("${currency.name} is deleted")
    }

    companion object{
        private val TAG: String = MyCurrenciesViewModel::class.java.simpleName
    }

}