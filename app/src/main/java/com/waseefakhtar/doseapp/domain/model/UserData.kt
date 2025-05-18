package com.waseefakhtar.doseapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val language: String = LanguageEnum.default().code
)
