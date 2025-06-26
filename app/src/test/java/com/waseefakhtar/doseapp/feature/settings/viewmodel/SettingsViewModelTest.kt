package com.waseefakhtar.doseapp.feature.settings.viewmodel

import com.waseefakhtar.doseapp.usecases.GetSelectedLanguageUseCase
import com.waseefakhtar.doseapp.usecases.SaveAppLanguageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {
    private val getSelectedLanguageUseCase = mock(GetSelectedLanguageUseCase::class.java)
    private val saveAppLanguageUseCase = mock(SaveAppLanguageUseCase::class.java)
    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `actualLanguage is updated when getSelectedLanguageUseCase emits value`() = runTest {
        `when`(getSelectedLanguageUseCase.execute()).thenReturn(flowOf("es"))
        viewModel = SettingsViewModel(getSelectedLanguageUseCase, saveAppLanguageUseCase)
        dispatcher.scheduler.advanceUntilIdle()
        assertEquals("es", viewModel.actualLanguage.value)
    }

    @Test
    fun `changeLanguage calls saveAppLanguageUseCase with correct code`() = runTest {
        `when`(getSelectedLanguageUseCase.execute()).thenReturn(flowOf("en"))
        viewModel = SettingsViewModel(getSelectedLanguageUseCase, saveAppLanguageUseCase)
        viewModel.changeLanguage("fr")
        dispatcher.scheduler.advanceUntilIdle()
        verify(saveAppLanguageUseCase).execute("fr")
    }
}
