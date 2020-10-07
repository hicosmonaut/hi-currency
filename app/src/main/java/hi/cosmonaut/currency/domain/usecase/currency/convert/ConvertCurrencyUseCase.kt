package hi.cosmonaut.currency.domain.usecase.currency.convert

import hi.cosmonaut.base.data.wrapper.Result
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.data.model.request.currency.convert.ConvertCurrency
import hi.cosmonaut.currency.data.model.response.currency.all.latest.AllCurrenciesResponse
import hi.cosmonaut.currency.data.source.remote.contract.NetworkContract

class ConvertCurrencyUseCase(
    private val network: NetworkContract.ICurrency,
    private val convertCurrency: ConvertCurrency
): IConvertCurrencyUseCase{
    override suspend fun invoke(): Result<Any> = when(val result = network.convert(convertCurrency)){
        is Result.Success<*> -> result
        is Result.Error<*> -> result
        else -> Result.Error(null, R.string.response_error_default, null)
    }
}