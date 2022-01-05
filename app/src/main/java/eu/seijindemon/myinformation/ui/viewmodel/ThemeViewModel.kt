package eu.seijindemon.myinformation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.seijindemon.myinformation.data.repository.DataStorePreferenceRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val dataStorePreferenceRepository: DataStorePreferenceRepository
): ViewModel() {

    private val _theme = MutableLiveData(0)
    var theme: LiveData<Int> = _theme

    init {
        viewModelScope.launch {
            dataStorePreferenceRepository.getTheme.collect {
                _theme.value = it
            }
        }
    }

    suspend fun saveTheme(theme: Int) {
        dataStorePreferenceRepository.setTheme(theme = theme)
    }

}