package com.raimbekov.rates.main.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.raimbekov.rates.DefaultRunner
import com.raimbekov.rates.main.domain.GetRatesUseCase
import com.raimbekov.rates.main.domain.model.Rate
import com.raimbekov.rates.main.view.model.RateViewData
import com.raimbekov.rates.main.view.model.RatesUpdateData
import io.mockk.*
import io.reactivex.Flowable
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(DefaultRunner::class)
class RatesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: RatesViewModel
    private lateinit var getRatesUseCase: GetRatesUseCase

    private lateinit var ratesSlot: MutableList<RatesUpdateData>
    private lateinit var ratesObserver: Observer<RatesUpdateData>

    @Before
    fun setUp() {
        getRatesUseCase = mockk()
        viewModel = RatesViewModel(getRatesUseCase)
        ratesObserver = mockk()
        ratesSlot = mutableListOf()
        viewModel.ratesLiveData.observeForever(ratesObserver)
    }

    @After
    fun tearDown() {
        viewModel.ratesLiveData.removeObserver(ratesObserver)
        unmockkAll()
    }

    @Test
    fun setAmount() {
        val currency = "EUR"
        val amount = 1.0
        every { ratesObserver.onChanged(capture(ratesSlot)) } just runs
        every { getRatesUseCase.getRates(currency, amount) } returns Flowable.just(stubRates2()).startWith(stubRates1())

        viewModel.start()

        assertTrue(ratesSlot.size == 2)
        val ratesUpdatedata1 = RatesUpdateData(
            mutableListOf(
                RateViewData("EUR", "1.0"),
                RateViewData("GBP", "0.9"),
                RateViewData("USD", "1.2"),
                RateViewData("CAD", "1.3")
            )
        )
        val ratesUpdatedata2 = RatesUpdateData(
            mutableListOf(
                RateViewData("EUR", "1.0"),
                RateViewData("GBP", "0.9"),
                RateViewData("USD", "1.2222"),
                RateViewData("CAD", "1.3333")
            )
        )

        assertEquals(ratesSlot.get(0), ratesUpdatedata1)
        assertEquals(ratesSlot.get(1), ratesUpdatedata2)
    }

    private fun stubRates1(): List<Rate> = mutableListOf(
        Rate("GBP", 0.9),
        Rate("USD", 1.2),
        Rate("CAD", 1.3)
    )

    private fun stubRates2(): List<Rate> = mutableListOf(
        Rate("GBP", 0.90001),
        Rate("USD", 1.22222),
        Rate("CAD", 1.33333)
    )
}