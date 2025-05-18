package com.waseefakhtar.doseapp.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waseefakhtar.doseapp.domain.model.LanguageEnum
import com.waseefakhtar.doseapp.usecases.GetSelectedLanguageUseCase
import com.waseefakhtar.doseapp.usecases.SaveAppLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSelectedLanguageUseCase: GetSelectedLanguageUseCase,
    private val saveAppLanguageUseCase: SaveAppLanguageUseCase
): ViewModel() {
    private val _actualLanguage = MutableStateFlow("")
    val actualLanguage = _actualLanguage

    init {
        getLanguageCode()
    }

    private fun getLanguageCode() {
        viewModelScope.launch {
            getSelectedLanguageUseCase.execute().collect { code ->
                _actualLanguage.update { LanguageEnum.getLabel(code) }
            }
        }
    }

    fun changeLanguage(languageCode: String) {
        viewModelScope.launch {
            saveAppLanguageUseCase.execute(languageCode)
        }
    }
}