package hi.cosmonaut.currency.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_currencies")
data class CurrencyEntity(
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "value") var value: String? = null
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long? = null
}