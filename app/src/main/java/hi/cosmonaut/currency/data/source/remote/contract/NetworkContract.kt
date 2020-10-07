package hi.cosmonaut.currency.data.source.remote.contract

import hi.cosmonaut.base.data.wrapper.Result
import hi.cosmonaut.currency.data.model.request.currency.base.BaseCurrency
import hi.cosmonaut.currency.data.model.request.currency.convert.ConvertCurrency

interface NetworkContract {
    interface ICurrency{
        suspend fun getAllCurrencies(): Result<Any>
        suspend fun getAllCurrenciesBySpecific(baseCurrency: BaseCurrency): Result<Any>
        suspend fun convert(convertCurrency: ConvertCurrency): Result<Any>
    }
}