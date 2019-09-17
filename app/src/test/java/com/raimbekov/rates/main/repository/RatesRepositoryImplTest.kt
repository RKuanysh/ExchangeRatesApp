package com.raimbekov.rates.main.repository

import com.raimbekov.rates.DefaultRunner
import com.raimbekov.rates.RatesData
import com.raimbekov.rates.main.domain.RatesRepository
import com.raimbekov.rates.main.repository.local.RatesLocalRepository
import com.raimbekov.rates.main.repository.remote.RatesRemoteRepository
import io.mockk.*
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(DefaultRunner::class)
class RatesRepositoryImplTest {

    private lateinit var ratesRepository: RatesRepository
    private lateinit var ratesRemoteRepository: RatesRemoteRepository
    private lateinit var ratesLocalRepository: RatesLocalRepository

    @Before
    fun setUp() {
        ratesLocalRepository = mockk()
        ratesRemoteRepository = mockk()
        ratesRepository = RatesRepositoryImpl(ratesRemoteRepository, ratesLocalRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `getRates - success`() {
        val currency = "EUR"
        every { ratesRemoteRepository.getRates(currency) } returns Flowable.just(stubRatesList())
        every { ratesLocalRepository.setRates(currency, any()) } just Runs
        every { ratesLocalRepository.getRates(currency) } returns emptyList()

        val subscription = ratesRepository.getRates(currency).test()

        subscription.assertValueCount(2)
            .assertValueAt(0, emptyList())
            .assertValueAt(1, stubRatesList())
            .dispose()

        verifyOrder {
            ratesRemoteRepository.getRates(any())
            ratesLocalRepository.getRates(any())
            ratesLocalRepository.setRates(any(), any())
        }

        confirmVerified(ratesRemoteRepository)
        confirmVerified(ratesLocalRepository)
    }

    @Test
    fun `getRates - remote error`() {
        val currency = "EUR"
        val error = Exception()
        val rates = stubRatesList()
        every { ratesRemoteRepository.getRates(currency) } returns Flowable.error(error)
        every { ratesLocalRepository.getRates(currency) } returns rates

        val subscription = ratesRepository.getRates(currency).test()

        subscription
            .assertValueCount(1)
            .assertValueAt(0, rates)
            .assertError(error)
            .dispose()

        verify(exactly = 0) { ratesLocalRepository.setRates(any(), any()) }
    }

    private fun stubRatesList() = RatesData.rates
}