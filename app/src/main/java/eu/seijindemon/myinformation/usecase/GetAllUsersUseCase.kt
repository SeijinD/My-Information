package eu.seijindemon.myinformation.usecase

import androidx.lifecycle.LiveData
import eu.seijindemon.myinformation.data.model.User
import eu.seijindemon.myinformation.data.repository.AppRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    operator fun invoke() : LiveData<List<User>> {
        return appRepository.getAllUsers()
    }

}