package eu.seijindemon.myinformation.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import eu.seijindemon.myinformation.data.model.User

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users_table ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM users_table WHERE id = :id")
    fun getUserById(id: Int): User?

}