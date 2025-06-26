package com.waseefakhtar.doseapp

import com.waseefakhtar.doseapp.usecases.GetSelectedLanguageUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.util.Locale

class MainActivityViewModelTest {
    private lateinit var getSelectedLanguageUseCase: GetSelectedLanguageUseCase
    private lateinit var viewModel: MainActivityViewModel

    @Before
    fun setUp() {
        getSelectedLanguageUseCase = mock(GetSelectedLanguageUseCase::class.java)
        viewModel = MainActivityViewModel(getSelectedLanguageUseCase)
    }

    @Test
    fun `getLanguageCode returns selected language when not blank`() = runBlocking {
        `when`(getSelectedLanguageUseCase.execute()).thenReturn(flowOf("es"))

        val result = viewModel.getLanguageCode()
        val value = result.first()

        assertEquals("es", value)
    }

    @Test
    fun `getLanguageCode returns default language when blank`() = runBlocking {
        `when`(getSelectedLanguageUseCase.execute()).thenReturn(flowOf(""))

        val result = viewModel.getLanguageCode()
        val value = result.first()

        assertEquals(Locale.getDefault().language, value)
    }
}
