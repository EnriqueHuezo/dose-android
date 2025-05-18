package com.waseefakhtar.doseapp.usecases

import com.waseefakhtar.doseapp.domain.model.UserData
import com.waseefakhtar.doseapp.domain.repository.UserRepository
import javax.inject.Inject

class SaveAppLanguageUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute(language: String) {
        userRepository.saveUserDataAsync(
            UserData(language = language)
        )
    }
}