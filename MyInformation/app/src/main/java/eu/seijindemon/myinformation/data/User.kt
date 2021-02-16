package eu.seijindemon.myinformation.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

// @Primary Key for Id
// @NonNull = Not Null Value

@Entity(tableName = "user_table")
data class User(

        @PrimaryKey(autoGenerate = true)  val id: Int = 0,
        val firstName: String,
        val lastName: String,
        val idNumber: String?,
        val afm: String?,
        val amka: String?

)