package eu.seijindemon.myinformation.data.repository

import androidx.lifecycle.LiveData
import eu.seijindemon.myinformation.data.local.db.AppDb
import eu.seijindemon.myinformation.data.model.KeyValue
import eu.seijindemon.myinformation.data.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val appDb: AppDb
) {

    suspend fun addUser(user: User) {
        appDb.appDao().addUser(user)
    }

    suspend fun updateUser(user: User) {
        appDb.appDao().updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        appDb.appDao().deleteUser(user)
    }

    fun updateUserKeysValues(keysValues: List<KeyValue>, id: Int) {
        appDb.appDao().updateUserKeysValues(keysValues, id)
    }

    fun getAllUsers(): LiveData<List<User>> {
        return appDb.appDao().getAllUsers()
    }

    fun getUserById(id: Int): User? {
        return appDb.appDao().getUserById(id)
    }
}