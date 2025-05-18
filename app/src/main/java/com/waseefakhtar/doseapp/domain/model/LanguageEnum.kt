package com.waseefakhtar.doseapp.domain.model

enum class LanguageEnum(
    val code: String,
    val label: String
) {
    ENGLISH("en", "English"),
    SPANISH("es", "Spanish"),
    AFGANISTAN_DARI("fa", "Afganistan Dari"),
    ITALIAN("it", "Italian");

    companion object {
        fun fromCode(code: String): LanguageEnum {
            return entries.firstOrNull { it.code == code } ?: ENGLISH
        }

        fun getLabel(code: String): String {
            return fromCode(code).label
        }

        fun default(): LanguageEnum {
            return ENGLISH
        }
    }
}