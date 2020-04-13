package com.mkielar.sklepallegro.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mkielar.sklepallegro.SingleEvent
import com.mkielar.sklepallegro.api.AllegroApiClient
import com.mkielar.sklepallegro.model.OfferDTO
import com.mkielar.sklepallegro.model.OffersDTO
import com.mkielar.sklepallegro.model.PriceDTO
import com.mkielar.sklepallegro.schedulers.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify


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
            override fun mainThread(): Scheduler = Schedulers.trampoline()
            override fun computation(): Scheduler = Schedulers.trampoline()
        })

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
                createOfferWithId("1")
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

    private fun createOfferWithId(id: String): OfferDTO {
        return OfferDTO(
            id,
            "Offer",
            "https://example.com/",
            PriceDTO(
                "100.00",
                "PLN"
            ),
            "<h1>Offer</h1>"
        )
    }

    private inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)

    private inline fun <reified T : Any> argumentCaptor(): ArgumentCaptor<T> =
        ArgumentCaptor.forClass(T::class.java)
}