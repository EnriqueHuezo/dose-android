package com.waseefakhtar.doseapp.domain.model

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
    fun `fromCode returns DEFAULT for invalid code`() {
        assertEquals(LanguageEnum.DEFAULT, LanguageEnum.fromCode("unknown"))
        assertEquals(LanguageEnum.DEFAULT, LanguageEnum.fromCode(""))
    }

    @Test
    fun `getLabel returns correct string`() {
        assertEquals("English", LanguageEnum.getLabel("en"))
        assertEquals("Spanish", LanguageEnum.getLabel("es"))
        assertEquals("Afganistan Dari", LanguageEnum.getLabel("fa"))
        assertEquals("Italian", LanguageEnum.getLabel("it"))
    }

    @Test
    fun `getLabel returns default label for invalid code`() {
        assertEquals("Default", LanguageEnum.getLabel("unknown"))
    }

    @Test
    fun `default returns DEFAULT`() {
        assertEquals(LanguageEnum.DEFAULT, LanguageEnum.default())
    }
}
