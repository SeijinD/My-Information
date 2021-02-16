package eu.seijindemon.myinformation.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyInfoViewModel(application: Application): AndroidViewModel(application) {

    private val getAllUsers: LiveData<List<User>>
    private val repository: MyInfoRepository

    init
    {
        val myInfoDao = MyInfoDatabase.getAppDataBase(application)?.myInfoDao()
        repository = MyInfoRepository(myInfoDao!!)
        getAllUsers = repository.getAllUsers
    }

    fun addUser(user: User)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.addUser(user)
        }
    }

    fun getAllUsers(): LiveData<List<User>> {
        return repository.getAllUsers
    }

    fun getUserById(id: Int): User {
        return repository.getUserById(id)
    }

}