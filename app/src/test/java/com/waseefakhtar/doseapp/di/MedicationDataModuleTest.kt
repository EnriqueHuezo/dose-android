package com.waseefakhtar.doseapp.di

import android.app.Application
import com.waseefakhtar.doseapp.data.MedicationDatabase
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class MedicationDataModuleTest {
    @Test
    fun `provideOkHttpClient returns OkHttpClient with logging interceptor`() {
        val client = MedicationDataModule.provideOkHttpClient()
        assertTrue(client.interceptors.any { it is HttpLoggingInterceptor })
    }

    @Test
    fun `provideMedicationDatabase no retorna nulo`() {
        val app = mock(Application::class.java)
        val db = MedicationDataModule.provideMedicationDatabase(app)
        assertNotNull(db)
    }

    @Test
    fun `provideMedicationRepository returns MedicationRepositoryImpl`() {
        val db = mock(MedicationDatabase::class.java)
        val dao = mock(com.waseefakhtar.doseapp.data.MedicationDao::class.java)
        `when`(db.dao).thenReturn(dao)
        val repo = MedicationDataModule.provideMedicationRepository(db)
        assertTrue(repo is com.waseefakhtar.doseapp.data.repository.MedicationRepositoryImpl)
    }
}
