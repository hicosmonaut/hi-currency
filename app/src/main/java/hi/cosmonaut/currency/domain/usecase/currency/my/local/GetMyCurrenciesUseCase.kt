package hi.cosmonaut.currency.domain.usecase.currency.my.local

import hi.cosmonaut.base.data.wrapper.Result
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.data.model.response.currency.all.latest.AllCurrenciesResponse
import hi.cosmonaut.currency.data.model.view.Currency
import hi.cosmonaut.currency.data.source.local.contract.DatabaseContract
import hi.cosmonaut.currency.data.source.remote.contract.NetworkContract

class GetMyCurrenciesUseCase(
    private val local: DatabaseContract
): IGetMyCurrenciesUseCase{
    override suspend fun invoke(): Result<Any> = when(val result = local.getMyCurrencies()){
        is Result.Success<*> -> handleSuccess(result.data as List<*>)
        is Result.Error<*> -> result
        else -> Result.Error(null, R.string.response_error_default, null)
    }

    private fun handleSuccess(list: List<*>): Result.Success<List<Currency>> {
        val currencies = list.filterIsInstance(Currency::class.java)
        return Result.Success(currencies)
    }
}