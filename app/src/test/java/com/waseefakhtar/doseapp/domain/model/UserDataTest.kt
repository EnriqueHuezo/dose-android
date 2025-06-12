package com.waseefakhtar.doseapp.domain.model

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class UserDataTest {
    @Test
    fun `default language is LanguageEnum default code`() {
        val userData = UserData()
        assertEquals(LanguageEnum.default().code, userData.language)
    }

    @Test
    fun `can serialize and deserialize UserData`() {
        val userData = UserData(language = "es")
        val json = Json.encodeToString(userData)
        val decoded = Json.decodeFromString<UserData>(json)
        assertEquals(userData, decoded)
    }
}
