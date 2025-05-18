package com.waseefakhtar.doseapp.usecases

import com.waseefakhtar.doseapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSelectedLanguageUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun execute(): Flow<String> {
        return userRepository.getUserDataLocal().map { it.language }
    }
}