package hi.cosmonaut.currency.data.source.remote

class NetworkConstants {

    companion object {

        private val TAG: String = NetworkConstants::class.java.simpleName

        //PROTOCOLS
        private const val PROTOCOL_HTTP = "http://"
        private const val PROTOCOL_HTTPS = "https://"

        //BASE URL
        const val BASE_URL_API = PROTOCOL_HTTPS + "api.exchangerate.host"

        //QUERY
        const val QUERY_PARAM_BASE = "base"
        const val QUERY_PARAM_FROM = "from"
        const val QUERY_PARAM_TO = "to"
        const val QUERY_PARAM_AMOUNT = "amount"
    }

}