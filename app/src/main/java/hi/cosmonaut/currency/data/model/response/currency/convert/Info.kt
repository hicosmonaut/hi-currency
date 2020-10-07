package hi.cosmonaut.currency.data.model.response.currency.convert


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("rate") var rate: Double? = null
)