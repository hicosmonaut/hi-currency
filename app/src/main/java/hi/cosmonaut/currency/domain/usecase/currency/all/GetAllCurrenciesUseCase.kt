package hi.cosmonaut.currency.domain.usecase.currency.all

import hi.cosmonaut.base.data.wrapper.Result
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.data.model.response.currency.all.latest.AllCurrenciesResponse
import hi.cosmonaut.currency.data.source.remote.contract.NetworkContract

class GetAllCurrenciesUseCase(
    private val network: NetworkContract.ICurrency
): IGetAllCurrenciesUseCase{
    override suspend fun invoke(): Result<Any> = when(val result = network.getAllCurrencies()){
        is Result.Success<*> -> handleSuccess(result.data as AllCurrenciesResponse)
        is Result.Error<*> -> result
        else -> Result.Error(null, R.string.response_error_default, null)
    }

    private fun handleSuccess(data: AllCurrenciesResponse): Result.Success<AllCurrenciesResponse> {
        //todo: actions with data, if needed
        return Result.Success(data)
    }
}