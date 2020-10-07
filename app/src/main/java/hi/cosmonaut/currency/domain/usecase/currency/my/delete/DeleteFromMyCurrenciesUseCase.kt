package hi.cosmonaut.currency.domain.usecase.currency.my.delete

import hi.cosmonaut.base.data.wrapper.Result
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.data.model.view.Currency
import hi.cosmonaut.currency.data.source.local.contract.DatabaseContract

class DeleteFromMyCurrenciesUseCase(
    private val local: DatabaseContract,
    private val currency: Currency
): IDeleteFromMyCurrenciesUseCase{
    override suspend fun invoke(): Result<Any> = when(val result = local.deleteCurrency(currency)){
        is Result.Success<*> -> Result.Success(result.data)
        is Result.Error<*> -> result
        else -> Result.Error(null, R.string.response_error_default, null)
    }
}