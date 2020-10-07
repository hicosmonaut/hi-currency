package hi.cosmonaut.currency.data.source.local.contract

import hi.cosmonaut.base.data.wrapper.Result
import hi.cosmonaut.currency.data.model.view.Currency

interface DatabaseContract {
    suspend fun getMyCurrencies(): Result<Any>
    suspend fun insertCurrency(currency: Currency): Result<Any>
    suspend fun updateCurrency(currency: Currency): Result<Any>
    suspend fun deleteCurrency(currency: Currency): Result<Any>
}