package com.raimbekov.rates.main.domain

import com.raimbekov.rates.DefaultRunner
import com.raimbekov.rates.RatesData
import com.raimbekov.rates.main.domain.model.Rate
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(DefaultRunner::class)
class GetRatesUseCaseTest {

    private lateinit var ratesRepository: RatesRepository
    private lateinit var getRatesUseCase: GetRatesUseCase

    @Before
    fun setUp() {
        ratesRepository = mockk()
        getRatesUseCase = GetRatesUseCase(ratesRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `getRates - empty rates list`() {
        every { ratesRepository.getRates(any()) } returns Flowable.just(emptyList())

        val subscription = getRatesUseCase.getRates("EUR", 1.0).test()

        subscription.assertValueCount(1)
            .assertValueAt(0, emptyList())
            .dispose()
    }

    @Test
    fun `getRates - non empty rates list`() {
        val rates = stubRatesList()

        every { ratesRepository.getRates(any()) } returns Flowable.just(rates)

        val subscription = getRatesUseCase.getRates("EUR", 1.0).test()

        subscription.assertValueCount(1)
            .assertValueAt(0, rates)
            .dispose()
    }

    @Test
    fun `getRates - amount multiplication`() {
        val amount = 2.0
        val rates = stubRatesList()
        val expectedRates = rates.map { it.copy(value = it.value * amount) }

        every { ratesRepository.getRates(any()) } returns Flowable.just(rates)

        val subscription = getRatesUseCase.getRates("EUR", amount).test()

        subscription.assertValueCount(1)
            .assertValueAt(0, expectedRates)
            .dispose()
    }

    @Test
    fun `getRates - flow of rates`() {
        val rates = stubRatesList()

        every { ratesRepository.getRates(any()) } returns Flowable.just(rates).startWith(emptyList<Rate>())

        val subscription = getRatesUseCase.getRates("EUR", 1.0).test()

        subscription.assertValueCount(2)
            .assertValueAt(0, emptyList())
            .assertValueAt(1, rates)
            .dispose()
    }

    @Test
    fun `getRates - error`() {
        val error = Exception()
        every { ratesRepository.getRates(any()) } returns Flowable.error(error)

        val subscription = getRatesUseCase.getRates("EUR", 1.0).test()

        subscription
            .assertError(error)
            .dispose()
    }

    private fun stubRatesList() = RatesData.rates
}