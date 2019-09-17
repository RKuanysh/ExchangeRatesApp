package com.raimbekov.rates.main.domain

import com.raimbekov.rates.DefaultRunner
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
        val currency = "EUR"
        every { ratesRepository.getRates(any()) } returns Flowable.just(emptyList())

        val subscription = getRatesUseCase.getRates(currency, 1.0).test()

        subscription.assertValueCount(1)
            .assertValueAt(0, emptyList())
    }

    @Test
    fun `getRates - non empty rates list`() {
        val currency = "EUR"
        val rates = stubRatesList()

        every { ratesRepository.getRates(any()) } returns Flowable.just(rates)

        val subscription = getRatesUseCase.getRates(currency, 1.0).test()

        subscription.assertValueCount(1)
            .assertValueAt(0, rates)
    }

    @Test
    fun `getRates - amount multiplication`() {
        val currency = "EUR"
        val amount = 2.0
        val rates = stubRatesList()
        val expectedRates = rates.map { it.copy(value = it.value * amount) }

        every { ratesRepository.getRates(any()) } returns Flowable.just(rates)

        val subscription = getRatesUseCase.getRates(currency, amount).test()

        subscription.assertValueCount(1)
            .assertValueAt(0, expectedRates)
    }

    @Test
    fun `getRates - flow of rates`() {
        val currency = "EUR"
        val rates = stubRatesList()

        every { ratesRepository.getRates(any()) } returns Flowable.just(rates).startWith(emptyList<Rate>())

        val subscription = getRatesUseCase.getRates(currency, 1.0).test()

        subscription.assertValueCount(2)
            .assertValueAt(0, emptyList())
            .assertValueAt(1, rates)
    }

    private fun stubRatesList(): List<Rate> {
        val list = mutableListOf<Rate>()
        list.add(Rate("AUD", 1.3))
        list.add(Rate("USD", 1.2))
        list.add(Rate("GBP", 0.9))
        return list
    }
}