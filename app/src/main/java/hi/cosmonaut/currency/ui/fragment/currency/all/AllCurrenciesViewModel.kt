package hi.cosmonaut.currency.ui.fragment.currency.all

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import hi.cosmonaut.base.data.wrapper.Result
import hi.cosmonaut.base.ui.viewmodel.feature.FeatureViewModel
import hi.cosmonaut.currency.data.model.request.currency.base.BaseCurrency
import hi.cosmonaut.currency.data.model.view.Currency
import hi.cosmonaut.currency.data.source.local.contract.DatabaseContract
import hi.cosmonaut.currency.data.source.local.database.DatabaseManager
import hi.cosmonaut.currency.data.source.remote.NetworkManager
import hi.cosmonaut.currency.data.source.remote.contract.NetworkContract
import hi.cosmonaut.currency.domain.usecase.currency.all.specific.GetAllCurrenciesBySpecificUseCase
import hi.cosmonaut.currency.domain.usecase.currency.all.specific.marked.GetAllCurrenciesBySpecificAndMarkMyCurrenciesUseCase
import hi.cosmonaut.currency.domain.usecase.currency.my.add.AddToMyCurrenciesUseCase
import hi.cosmonaut.currency.domain.usecase.currency.my.delete.DeleteFromMyCurrenciesUseCase
import hi.cosmonaut.currency.domain.usecase.currency.my.local.GetMyCurrenciesUseCase
import hi.cosmonaut.currency.ui.fragment.home.HomeFragmentDirections
import hi.cosmonaut.currency.util.CurrencyUtils
import hi.cosmonaut.currency.util.LocaleUtils
import kotlinx.coroutines.launch


class AllCurrenciesViewModel: FeatureViewModel(){

    private val network: NetworkContract.ICurrency = NetworkManager()

    private val _adapterData: MutableLiveData<List<Currency>> = MutableLiveData<List<Currency>>()
    val adapterData: LiveData<List<Currency>> get() = _adapterData

    private val _navigationHomeData: MutableLiveData<NavDirections> = MutableLiveData<NavDirections>()
    val navigationHomeData: LiveData<NavDirections> get() = _navigationHomeData

    fun fetchData(context: Context){
        val locale = LocaleUtils.getCurrentLocale(context)
        val currencyByLocale = CurrencyUtils.getCurrencyByLocale(locale)
        val baseCurrency = BaseCurrency(currencyByLocale)
        Log.d(TAG, "fetchData: locale: $locale")
        Log.d(TAG, "fetchData: currency: $baseCurrency")

        val local: DatabaseContract = DatabaseManager(context)

        val getMyCurrenciesUseCase =  GetMyCurrenciesUseCase(local)
        val getAllCurrenciesBySpecificUseCase = GetAllCurrenciesBySpecificUseCase(network, baseCurrency)
        val getAllCurrenciesBySpecificAndMarkMyCurrenciesUseCase = GetAllCurrenciesBySpecificAndMarkMyCurrenciesUseCase(
            getAllCurrenciesBySpecificUseCase,
            getMyCurrenciesUseCase
        )

        viewModelScope.launch {
            when(val result = getAllCurrenciesBySpecificAndMarkMyCurrenciesUseCase()){
                is Result.Success<*> -> handlesAsAllCurrenciesBySpecific(result.data as List<*>)
                is Result.Error<*> -> sendMessage(result.messageId)
            }
        }
    }

    private fun handlesAsAllCurrenciesBySpecific(list: List<*>) {
        val currencies = list.filterIsInstance(Currency::class.java)
        sendDataToAdapter(currencies)
    }

    private fun sendDataToAdapter(currencies: List<Currency>) = _adapterData.postValue(currencies)

    fun navigateToSelectedCurrency() = navigateHome(HomeFragmentDirections.actionHomeToCurrency())

    private fun navigateHome(action: NavDirections) = _navigationHomeData.postValue(action)

    fun performItemAction(context: Context, currency: Currency) {
        val local: DatabaseContract = DatabaseManager(context)

        if(currency.isFavorite == true){
            addToFavorites(local, currency)
        } else {
            deleteFromFavorites(local, currency)
        }

    }

    private fun addToFavorites(local: DatabaseContract, currency: Currency) {
        val addToMyFavoritesUseCase = AddToMyCurrenciesUseCase(local, currency)

        viewModelScope.launch {
            when(val result = addToMyFavoritesUseCase()){
                is Result.Success<*> -> {
                    val c = result.data as Currency
                    sendMessage("${c.name} is added")
                }
                is Result.Error<*> -> sendMessage(result.messageId)
            }
        }
    }

    private fun deleteFromFavorites(local: DatabaseContract, currency: Currency) {
        val deleteFromMyCurrenciesUseCase = DeleteFromMyCurrenciesUseCase(local, currency)

        viewModelScope.launch {
            when(val result = deleteFromMyCurrenciesUseCase()){
                is Result.Success<*> -> {
                    val c = result.data as Currency
                    sendMessage("${c.name} is deleted")
                }
                is Result.Error<*> -> sendMessage(result.messageId)
            }
        }

    }

    companion object{
        private val TAG: String = AllCurrenciesViewModel::class.java.simpleName
    }
}