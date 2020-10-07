package hi.cosmonaut.currency.data.model.response.currency.convert


import com.google.gson.annotations.SerializedName

data class ConvertCurrencyResponse(
    @SerializedName("date") var date: String? = null,
    @SerializedName("historical") var historical: Boolean? = false,
    @SerializedName("info") var info: Info? = null,
    @SerializedName("query") var query: Query? = null,
    @SerializedName("result") var result: Double? = null,
    @SerializedName("success") var success: Boolean? = false
)