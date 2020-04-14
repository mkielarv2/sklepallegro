package com.mkielar.sklepallegro.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mkielar.sklepallegro.util.SingleEvent
import com.mkielar.sklepallegro.api.AllegroApiClient
import com.mkielar.sklepallegro.model.OfferDTO
import com.mkielar.sklepallegro.model.OffersDTO
import com.mkielar.sklepallegro.model.PriceDTO
import com.mkielar.sklepallegro.schedulers.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.verification.VerificationMode


class ListingViewModelTest {
    private lateinit var allegroApiClient: AllegroApiClient
    private lateinit var listingViewModel: ListingViewModel

    private lateinit var offerObserver: Observer<List<OfferDTO>>
    private lateinit var errorObserver: Observer<SingleEvent<Throwable>>


    @Rule
    @JvmField
    val executorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        allegroApiClient = mock()
        listingViewModel = ListingViewModel(allegroApiClient, object : SchedulerProvider {
            override fun io(): Scheduler = Schedulers.trampoline()
            override fun ui(): Scheduler = Schedulers.trampoline()
        }, false)

        offerObserver = mock()
        errorObserver = mock()
        listingViewModel.offersLiveData.observeForever(offerObserver)
        listingViewModel.errorLiveData.observeForever(errorObserver)
    }

    @Test
    fun testObserverConfiguration() {
        assertNotNull(listingViewModel.offersLiveData)
        assertNotNull(listingViewModel.errorLiveData)
        assertTrue(listingViewModel.offersLiveData.hasObservers())
        assertTrue(listingViewModel.errorLiveData.hasObservers())
    }

    @Test
    fun testDataFetchSuccess() {
        //given
        val data = OffersDTO(
            listOf(
                createOfferFrom("1")
            )
        )

        `when`(allegroApiClient.getData()).thenReturn(Flowable.just(data))

        //when
        listingViewModel.fetch()

        //then
        verify(offerObserver).onChanged(data.offers)
    }

    @Test
    fun testDataFetchFail() {
        //given
        val throwable = Throwable("Generic error")
        `when`(allegroApiClient.getData()).thenReturn(Flowable.error(throwable))

        //when
        listingViewModel.fetch()

        //then
        val captor = argumentCaptor<SingleEvent<Throwable>>()
        verify(errorObserver).onChanged(captor.capture())
        assertEquals(throwable, captor.value.consume())
        //assert that error can be consumed only once
        assertNull(captor.value.consume())
    }

    @Test
    fun testDefaultPriceFilter() {
        //given
        val expected = createOfferFrom("2", priceDTO = PriceDTO("500.00", "PLN"))
        val data = OffersDTO(
            listOf(
                createOfferFrom("1", priceDTO = PriceDTO("40.00", "PLN")),
                expected,
                createOfferFrom("3", priceDTO = PriceDTO("1200.00", "PLN"))
            )
        )

        `when`(allegroApiClient.getData()).thenReturn(Flowable.just(data))

        //when
        listingViewModel.fetch()

        //then
        val captor = argumentCaptor<List<OfferDTO>>()
        verify(offerObserver).onChanged(captor.capture())
        assertEquals(1, captor.value.size)
        assertEquals(expected, captor.value[0])
    }

    @Test
    fun testCustomPriceFilter() {
        //given
        val expected = createOfferFrom("2", priceDTO = PriceDTO("15000.00", "PLN"))
        val data = OffersDTO(
            listOf(
                createOfferFrom("1", priceDTO = PriceDTO("40.00", "PLN")),
                expected,
                createOfferFrom("3", priceDTO = PriceDTO("1200.00", "PLN")),
                createOfferFrom("4", priceDTO = PriceDTO("9999.99", "PLN")),
                createOfferFrom("5", priceDTO = PriceDTO("25000.00", "PLN"))
            )
        )

        `when`(allegroApiClient.getData()).thenReturn(Flowable.just(data))

        //when
        listingViewModel.fetch()
        listingViewModel.priceFrom = 10000.0
        listingViewModel.priceTo = 20000.0
        listingViewModel.applyFilter()

        //then
        val captor = argumentCaptor<List<OfferDTO>>()
        verify(offerObserver, times(2)).onChanged(captor.capture())
        val filtered = captor.allValues[1]
        assertEquals(1, filtered.size)
        assertEquals(expected, filtered[0])
    }

    @After
    fun tearDown() {
        listingViewModel.clear()
        listingViewModel.dispose()
    }

    private fun createOfferFrom(
        id: String,
        name: String = "Offer",
        thumbnailUrl: String = "https://example.com/",
        priceDTO: PriceDTO = PriceDTO(
            "100.00",
            "PLN"
        ),
        description: String = "<h1>Offer</h1>"
    ): OfferDTO {
        return OfferDTO(
            id,
            name,
            thumbnailUrl,
            priceDTO,
            description
        )
    }

    private inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)

    private inline fun <reified T : Any> argumentCaptor(): ArgumentCaptor<T> =
        ArgumentCaptor.forClass(T::class.java)
}