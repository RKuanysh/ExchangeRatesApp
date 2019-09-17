package com.raimbekov.rates.main.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raimbekov.rates.DefaultRunner
import com.raimbekov.rates.RatesData
import com.raimbekov.rates.main.domain.GetRatesUseCase
import com.raimbekov.rates.main.domain.model.Rate
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import org.junit.After
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

    @Before
    fun setUp() {
        getRatesUseCase = mockk()
        viewModel = RatesViewModel(getRatesUseCase)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun setAmount() {
        val currency = "EUR"
        val amount = 1.0
        every { getRatesUseCase.getRates(currency, amount) } returns Flowable.just(stubRatesList())

        viewModel.start()

        assertTrue(viewModel.ratesLiveData.value != null)
    }

    private fun stubRatesList(): List<Rate> = RatesData.rates
}