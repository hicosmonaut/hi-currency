package hi.cosmonaut.currency.data.source.remote

import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import hi.cosmonaut.base.data.wrapper.Result
import hi.cosmonaut.currency.R
import hi.cosmonaut.currency.data.model.request.currency.base.BaseCurrency
import hi.cosmonaut.currency.data.model.request.currency.convert.ConvertCurrency
import hi.cosmonaut.currency.data.model.view.Currency
import hi.cosmonaut.currency.data.source.remote.api.Api
import hi.cosmonaut.currency.data.source.remote.contract.NetworkContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class NetworkManager : NetworkContract.ICurrency {

    private val retrofit by lazy {
        RetrofitHelper.getRetrofit(NetworkConstants.BASE_URL_API).create(Api::class.java)
    }

    override suspend fun getAllCurrencies(): Result<Any> = executeRequest {
        retrofit.getLatestCurrencies()
    }

    override suspend fun getAllCurrenciesBySpecific(baseCurrency: BaseCurrency): Result<Any> {

        val response = executeRequest {
            retrofit.getLatestCurrenciesBySpecific(
                baseCurrency.base
            )
        }

        return when (response) {
            is Result.Error<*> -> response
            is Result.Success<*> -> {
                val jsonObject: JsonObject =
                    JsonParser().parse(response.data as String).asJsonObject
                val ratesObject = jsonObject.getAsJsonObject("rates")

                if (ratesObject != null) {

                    val currencies = ratesObject
                        .keySet()
                        .filterNotNull()
                        .map { rateName ->
                            Currency(
                                rateName,
                                ratesObject.get(rateName)?.asString
                            )
                        }

                    Result.Success(currencies)
                } else {
                    Result.Error(null, R.string.response_error_failed, null)
                }

            }

            else -> Result.Error(null, R.string.response_error_failed, null)
        }
    }

    override suspend fun convert(convertCurrency: ConvertCurrency): Result<Any> {
        val response = executeRequest {
            retrofit.convert(
                convertCurrency.from,
                convertCurrency.to,
                convertCurrency.amount
            )
        }

        return when (response) {
            is Result.Error<*> -> response
            is Result.Success<*> -> {
                val jsonObject: JsonObject = JsonParser().parse(response.data as String).asJsonObject
                val jsonResult = jsonObject.get("result")

                val result = if(jsonResult.isJsonNull){
                    "0"
                } else {
                    jsonResult.asString
                }

                Result.Success(result)
            }

            else -> Result.Error(null, R.string.response_error_failed, null)
        }
    }


    private suspend fun <T : Any> executeRequest(request: suspend () -> Response<T>) =
        withContext(Dispatchers.IO) {
            try {
                val response = request()
                val body = response.body()

                if (response.isSuccessful) {
                    Result.Success(body as T)
                } else {
                    Result.Error(
                        null,
                        R.string.response_error_failed,
                        Exception("Code: ${response.code()}.")
                    )
                }

            } catch (e: Exception) {
                Log.d(TAG, "executeRequest: catch: $e")
                Result.Error(null, R.string.response_error_default, e)
            }
        }

    companion object {
        private val TAG: String = NetworkManager::class.java.simpleName
    }
}