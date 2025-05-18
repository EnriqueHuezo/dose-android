package com.waseefakhtar.doseapp.domain.repository

import com.waseefakhtar.doseapp.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserDataLocal(): Flow<UserData>
    suspend fun saveUserDataAsync(userData: UserData)
}