package hi.cosmonaut.currency.util

import android.content.Context
import android.os.Build
import java.util.*

class CurrencyUtils {
    companion object{
        private val TAG: String = CurrencyUtils::class.java.simpleName

        fun getCurrencyByLocale(locale: Locale): String = Currency.getInstance(locale).currencyCode

    }
}


