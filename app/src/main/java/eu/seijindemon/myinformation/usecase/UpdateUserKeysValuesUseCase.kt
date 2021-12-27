package eu.seijindemon.myinformation.usecase

import eu.seijindemon.myinformation.data.model.KeyValue
import eu.seijindemon.myinformation.data.model.User
import eu.seijindemon.myinformation.data.repository.AppRepository
import javax.inject.Inject

class UpdateUserKeysValuesUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    operator fun invoke(keysValues: List<KeyValue>, id: Int) {
        return appRepository.updateUserKeysValues(keysValues, id)
    }

}