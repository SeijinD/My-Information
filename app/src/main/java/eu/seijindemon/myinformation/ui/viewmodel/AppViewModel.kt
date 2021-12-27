package eu.seijindemon.myinformation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.seijindemon.myinformation.data.model.KeyValue
import eu.seijindemon.myinformation.data.model.User
import eu.seijindemon.myinformation.data.repository.AppRepository
import eu.seijindemon.myinformation.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val updateUserKeysValuesUseCase: UpdateUserKeysValuesUseCase
): ViewModel() {

//    private var _users = MutableLiveData<List<User>>()
//    var users: LiveData<List<User>> = _users

    private var _users = getAllUsersUseCase.invoke()
    var users: LiveData<List<User>> = _users

    private var _user = MutableLiveData<User>()
    var user: LiveData<User> = _user

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            addUserUseCase.invoke(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            updateUserUseCase.invoke(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUserUseCase.invoke(user)
        }
    }

    fun updateUserKeysValues(keysValues: List<KeyValue>, id: Int) {
        updateUserKeysValuesUseCase.invoke(keysValues, id)
    }

//    fun getAllUsers() {
//        _users.value = getAllUsersUseCase.invoke().value
//    }

    fun getUserById(id: Int) {
        _user.value = getUserByIdUseCase.invoke(id)
    }

}