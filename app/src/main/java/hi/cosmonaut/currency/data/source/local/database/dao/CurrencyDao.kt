package hi.cosmonaut.currency.data.source.local.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import hi.cosmonaut.currency.data.source.local.database.entity.CurrencyEntity


@Dao
interface CurrencyDao{
    @Insert(onConflict = REPLACE)
    suspend fun insert(currency: CurrencyEntity): Long

    @Update(onConflict = REPLACE)
    suspend fun update(currency: CurrencyEntity)

    @Delete
    suspend fun delete(currency: CurrencyEntity)

    @Query("DELETE FROM my_currencies WHERE name = :currencyName")
    suspend fun delete(currencyName: String)

    @Query("SELECT * FROM my_currencies ORDER BY name")
    suspend fun getMyCurrencies(): List<CurrencyEntity>


}