package hi.cosmonaut.currency.data.model.view

data class Currency(
    val name: String? = null,
    val value: String? = null,
    var isFavorite: Boolean? = false
)