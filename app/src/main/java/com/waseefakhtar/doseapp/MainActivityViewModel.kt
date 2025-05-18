package com.waseefakhtar.doseapp

import androidx.lifecycle.ViewModel
import com.waseefakhtar.doseapp.usecases.GetSelectedLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getSelectedLanguageUseCase: GetSelectedLanguageUseCase
) : ViewModel() {
    fun getLanguageCode(): Flow<String> =
        getSelectedLanguageUseCase.execute()
            .flowOn(Dispatchers.IO)
            .map { it.ifBlank { Locale.getDefault().language } }
}