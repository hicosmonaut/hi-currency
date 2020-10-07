package hi.cosmonaut.currency.data.model.request.currency.convert

data class ConvertCurrency(
    var from: String? = null,
    var to: String? = null,
    var amount: String? = null
)