package eu.seijindemon.myinformation.usecase

import eu.seijindemon.myinformation.data.model.User
import eu.seijindemon.myinformation.data.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    operator fun invoke(id: Int) : User? {
        return appRepository.getUserById(id)
    }

}