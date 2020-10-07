package hi.cosmonaut.currency.data.model.response.currency.all.latest


import com.google.gson.annotations.SerializedName

data class AllCurrenciesResponse(
    @SerializedName("base") var base: String? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("rates") var rates: Rates? = null,
    @SerializedName("success") var success: Boolean? = false
)