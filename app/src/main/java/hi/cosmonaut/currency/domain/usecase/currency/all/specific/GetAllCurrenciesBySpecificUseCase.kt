package hi.cosmonaut.currency.domain.usecase.currency.all.specific

import hi.cosmonaut.base.data.wrapper.Result
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.data.model.request.currency.base.BaseCurrency
import hi.cosmonaut.currency.data.model.view.Currency
import hi.cosmonaut.currency.data.source.remote.contract.NetworkContract

class GetAllCurrenciesBySpecificUseCase(
    private val network: NetworkContract.ICurrency,
    private val baseCurrency: BaseCurrency
): IGetAllCurrenciesBySpecificUseCase{

    override suspend fun invoke(): Result<Any> = when(val result = network.getAllCurrenciesBySpecific(baseCurrency)){
        is Result.Success<*> -> handleSuccess(result.data as List<*>)
        is Result.Error<*> -> result
        else -> Result.Error(null, R.string.response_error_default, Exception("Else error in $TAG"))
    }

    private fun handleSuccess(list: List<*>): Result.Success<List<Currency>> {
        val currencies = list.filterIsInstance<Currency>()
        return Result.Success(currencies)
    }

    companion object{
        private val TAG: String = GetAllCurrenciesBySpecificUseCase::class.java.simpleName
    }
}