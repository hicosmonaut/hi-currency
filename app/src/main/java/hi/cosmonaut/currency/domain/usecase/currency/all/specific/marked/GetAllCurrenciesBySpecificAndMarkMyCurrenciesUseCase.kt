package hi.cosmonaut.currency.domain.usecase.currency.all.specific.marked

import hi.cosmonaut.base.data.wrapper.Result
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.data.model.view.Currency
import hi.cosmonaut.currency.domain.usecase.currency.all.specific.IGetAllCurrenciesBySpecificUseCase
import hi.cosmonaut.currency.domain.usecase.currency.my.local.IGetMyCurrenciesUseCase

class GetAllCurrenciesBySpecificAndMarkMyCurrenciesUseCase(
    private val getAllCurrenciesBySpecificUseCase: IGetAllCurrenciesBySpecificUseCase,
    private val getMyCurrenciesUseCase: IGetMyCurrenciesUseCase
): IGetAllCurrenciesBySpecificAndMarkMyCurrenciesUseCase{

    override suspend fun invoke(): Result<Any> = when(val result = getAllCurrenciesBySpecificUseCase()){
        is Result.Success<*> -> handleAllCurrencies(result.data as List<*>)
        is Result.Error<*> -> result
        else -> Result.Error(null, R.string.response_error_default, null)
    }

    private suspend fun handleAllCurrencies(list: List<*>): Result<Any> {
        val currenciesAll = list.filterIsInstance<Currency>()

        return when(val result = getMyCurrenciesUseCase()) {
            is Result.Success<*> -> handleMyAndAllCurrencies(result.data as List<*>, currenciesAll)
            else -> Result.Success(currenciesAll)
        }
    }

    private fun handleMyAndAllCurrencies(list: List<*>, currenciesAll: List<Currency>): Result.Success<List<Currency>> {
        val myCurrenciesNames = list.filterIsInstance<Currency>().mapNotNull { it.name }
        val mutableAllCurrencies = currenciesAll.toMutableList()

        mutableAllCurrencies.forEach {
            if(myCurrenciesNames.contains(it.name)) {
                it.isFavorite = true
            }
        }

        return Result.Success(mutableAllCurrencies.toList())
    }

    companion object{
        private val TAG: String = GetAllCurrenciesBySpecificAndMarkMyCurrenciesUseCase::class.java.simpleName
    }
}