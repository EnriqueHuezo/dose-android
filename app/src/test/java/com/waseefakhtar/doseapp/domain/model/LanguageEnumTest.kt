package com.waseefakhtar.doseapp.domain.model

import com.waseefakhtar.doseapp.R
import org.junit.Assert.assertEquals
import org.junit.Test

class LanguageEnumTest {
    @Test
    fun `fromCode returns correct enum for valid codes`() {
        assertEquals(LanguageEnum.ENGLISH, LanguageEnum.fromCode("en"))
        assertEquals(LanguageEnum.SPANISH, LanguageEnum.fromCode("es"))
        assertEquals(LanguageEnum.AFGANISTAN_DARI, LanguageEnum.fromCode("fa"))
        assertEquals(LanguageEnum.ITALIAN, LanguageEnum.fromCode("it"))
    }

    @Test
    fun `fromCode returns ENGLISH for invalid code`() {
        assertEquals(LanguageEnum.ENGLISH, LanguageEnum.fromCode("unknown"))
        assertEquals(LanguageEnum.ENGLISH, LanguageEnum.fromCode(""))
    }

    @Test
    fun `getLabel returns correct string resource id`() {
        assertEquals(R.string.language_english, LanguageEnum.getLabel("en"))
        assertEquals(R.string.language_spanish, LanguageEnum.getLabel("es"))
        assertEquals(R.string.language_afganistan_dari, LanguageEnum.getLabel("fa"))
        assertEquals(R.string.language_italian, LanguageEnum.getLabel("it"))
    }

    @Test
    fun `getLabel returns english label for invalid code`() {
        assertEquals(R.string.language_english, LanguageEnum.getLabel("unknown"))
    }

    @Test
    fun `default returns ENGLISH`() {
        assertEquals(LanguageEnum.ENGLISH, LanguageEnum.default())
    }
}
