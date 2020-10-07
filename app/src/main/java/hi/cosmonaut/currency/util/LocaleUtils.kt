package hi.cosmonaut.currency.util

import android.content.Context
import android.os.Build
import java.util.*

class LocaleUtils {
    companion object{
        private val TAG: String = LocaleUtils::class.java.simpleName

        fun getCurrentLocale(context: Context): Locale {

            val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.resources.configuration.locales.get(0)
            } else {
                context.resources.configuration.locale
            }

            return locale ?: Locale.getDefault()
        }

    }
}


