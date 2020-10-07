package hi.cosmonaut.currency.data.source.local.database

import android.content.Context
import android.util.Log
import hi.cosmonaut.base.data.wrapper.Result
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.data.model.view.Currency
import hi.cosmonaut.currency.data.source.local.contract.DatabaseContract
import hi.cosmonaut.currency.data.source.local.database.entity.CurrencyEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseManager(context: Context) : DatabaseContract {

    private val database by lazy {
        AppDatabase.getInstance(context)
    }

    private val dao by lazy {
        database?.currencyDao()
    }

    override suspend fun getMyCurrencies(): Result<Any> = withContext(Dispatchers.IO) {

        val result = executeRequest { dao?.getMyCurrencies() }

        when (result) {
            is Result.Success<*> -> {
                val list = result.data as List<*>
                val currencies = list
                    .filterIsInstance(CurrencyEntity::class.java)
                    .map { Currency(it.name, it.value, true) }

                    Result.Success(currencies)
            }
            else -> result
        }
    }

    override suspend fun insertCurrency(currency: Currency): Result<Any> =
        withContext(Dispatchers.IO) {
            val data = CurrencyEntity(currency.name, currency.value)
            val result = executeRequest { dao?.insert(data) }

            when (result) {
                is Result.Success<*> -> Result.Success(currency)
                else -> result
            }
        }

    override suspend fun updateCurrency(currency: Currency): Result<Any> =
        withContext(Dispatchers.IO) {
            val data = CurrencyEntity(currency.name, currency.value)
            val result = executeRequest { dao?.update(data) }

            when (result) {
                is Result.Success<*> -> Result.Success(currency)
                else -> result
            }
        }

    override suspend fun deleteCurrency(currency: Currency): Result<Any> =
        withContext(Dispatchers.IO) {
            val data = CurrencyEntity(currency.name, currency.value)
            val result = executeRequest {
                data.name?.let{
                    dao?.delete(it)
                }
            }

            when (result) {
                is Result.Success<*> -> Result.Success(currency)
                else -> result
            }
        }

    private suspend fun <T> executeRequest(request: suspend () -> T): Result<Any> =
        try {
            Result.Success(request())
        } catch (e: Exception) {
            Result.Error(null, R.string.database_error_request_failed, e)
        }

}