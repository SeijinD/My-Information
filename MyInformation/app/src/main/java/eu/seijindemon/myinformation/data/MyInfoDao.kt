package eu.seijindemon.myinformation.data

import androidx.lifecycle.LiveData
import androidx.room.*

// Create Dao and write all methods

@Dao
interface MyInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun  addUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE id == :id")
    fun getUserById(id: Int): User

}