package hi.cosmonaut.currency.data.source.remote.api

import hi.cosmonaut.currency.data.source.remote.NetworkConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/latest")
    suspend fun getLatestCurrencies(): Response<String>

    @GET("/latest")
    suspend fun getLatestCurrenciesBySpecific(
        @Query(NetworkConstants.QUERY_PARAM_BASE) base: String?
    ): Response<String>

    @GET("/convert")
    suspend fun convert(
        @Query(NetworkConstants.QUERY_PARAM_FROM) from: String?,
        @Query(NetworkConstants.QUERY_PARAM_TO) to: String?,
        @Query(NetworkConstants.QUERY_PARAM_AMOUNT) amount: String?
    ): Response<String>


}