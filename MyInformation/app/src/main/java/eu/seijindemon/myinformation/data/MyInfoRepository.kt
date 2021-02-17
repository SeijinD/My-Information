package eu.seijindemon.myinformation.data

import androidx.lifecycle.LiveData

class MyInfoRepository(private val myInfoDao: MyInfoDao) {

    val getAllUsers: LiveData<List<User>> = myInfoDao.getAllUsers()

    fun addUser(user: User)
    {
        myInfoDao.addUser(user)
    }

    fun deleteUser(user: User)
    {
        myInfoDao.deleteUser(user)
    }

    fun getUserById(id: Int): User
    {
        return myInfoDao.getUserById(id)
    }


}