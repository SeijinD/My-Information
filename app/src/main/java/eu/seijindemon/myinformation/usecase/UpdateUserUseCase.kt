package eu.seijindemon.myinformation.usecase

import eu.seijindemon.myinformation.data.model.User
import eu.seijindemon.myinformation.data.repository.AppRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend operator fun invoke(user: User) {
        return appRepository.updateUser(user)
    }

}