package eu.seijindemon.myinformation.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "first_name") var firstName: String,
    @ColumnInfo(name = "last_name") var lastName: String,
    @ColumnInfo(name = "keys_values") var keysValues: List<KeyValue>?
)

@Entity(tableName = "user_keys_values_table")
data class KeyValue(
    @ColumnInfo(name = "key") var key: String,
    @ColumnInfo(name = "value") var value: String
)
