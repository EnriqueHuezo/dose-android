package com.waseefakhtar.doseapp.domain.model

import androidx.annotation.StringRes
import com.waseefakhtar.doseapp.R

enum class LanguageEnum(
    val code: String,
    @StringRes val label: Int
) {
    ENGLISH("en", R.string.language_english),
    SPANISH("es", R.string.language_spanish),
    AFGANISTAN_DARI("fa", R.string.language_afganistan_dari),
    ITALIAN("it", R.string.language_italian);

    companion object {
        fun fromCode(code: String): LanguageEnum {
            return entries.firstOrNull { it.code == code } ?: ENGLISH
        }

        fun getLabel(code: String): Int {
            return fromCode(code).label
        }

        fun default(): LanguageEnum {
            return ENGLISH
        }
    }
}
