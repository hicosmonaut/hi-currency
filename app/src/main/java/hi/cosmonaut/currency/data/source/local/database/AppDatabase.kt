package hi.cosmonaut.currency.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hi.cosmonaut.currency.data.source.local.database.dao.CurrencyDao
import hi.cosmonaut.currency.data.source.local.database.entity.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao?

    companion object {
        private val TAG: String = AppDatabase::class.java.simpleName

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context?): AppDatabase? {
            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(
                        context!!,
                        AppDatabase::class.java,
                        DatabaseConstants.DATABASE_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!
            }
        }

    }


}