package com.waseefakhtar.doseapp.data.repository

import com.waseefakhtar.doseapp.domain.model.UserData
import com.waseefakhtar.doseapp.domain.repository.UserRepository
import com.waseefakhtar.doseapp.preferences.DataStoreHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val dataStore: DataStoreHandler
): UserRepository {
    override fun getUserDataLocal(): Flow<UserData> =
        dataStore.getValue(DataStoreHandler.USER_DATA, UserData())

    override suspend fun saveUserDataAsync(userData: UserData) =
        dataStore.saveValue(DataStoreHandler.USER_DATA, userData)

}