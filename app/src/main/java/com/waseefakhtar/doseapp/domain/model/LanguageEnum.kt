package com.waseefakhtar.doseapp.domain.model

import java.util.Locale

enum class LanguageEnum(
    val code: String,
    val label: String
) {
    DEFAULT("dt", "Default"),
    ENGLISH("en", "English"),
    SPANISH("es", "Spanish"),
    AFGANISTAN_DARI("fa", "Afganistan Dari"),
    ITALIAN("it", "Italian");

    companion object {
        fun fromCode(code: String): LanguageEnum {
            return entries.firstOrNull { it.code == code } ?: DEFAULT
        }

        fun getLabel(code: String): String {
            return fromCode(code).label
        }

        fun default(): LanguageEnum {
            return DEFAULT
        }

        fun resolveEffectiveCode(storedCode: String): String {
            if (storedCode.isNotBlank()) return storedCode

            val systemLang = Locale.getDefault().language
            val isSupported = entries.any { it.code == systemLang && it != DEFAULT }

            return if (isSupported) DEFAULT.code else ENGLISH.code
        }
    }
}
