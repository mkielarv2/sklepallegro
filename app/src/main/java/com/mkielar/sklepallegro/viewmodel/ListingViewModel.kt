package com.mkielar.sklepallegro.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkielar.sklepallegro.api.AllegroApiClient
import com.mkielar.sklepallegro.model.OfferDTO
import com.mkielar.sklepallegro.schedulers.SchedulerProvider
import com.mkielar.sklepallegro.util.SingleEvent
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class ListingViewModel(
    private val allegroApiClient: AllegroApiClient,
    private val schedulerProvider: SchedulerProvider,
    fetchOnInit: Boolean = true
) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val allOffersLiveData: MutableLiveData<List<OfferDTO>> = MutableLiveData()
    val offersLiveData: MediatorLiveData<List<OfferDTO>> = MediatorLiveData()

    val errorLiveData: MutableLiveData<SingleEvent<Throwable>> = MutableLiveData()

    var priceFrom: Double = 50.0
    var priceTo: Double = 1000.0

    init {
        if (fetchOnInit) {
            fetch()
        }

        offersLiveData.addSource(allOffersLiveData) {
            offersLiveData.value =
                filter(it).sortedBy { offerDTO -> offerDTO.price.amount.toDouble() }
        }
    }

    fun fetch() {
        compositeDisposable.add(
            allegroApiClient.getData()
                .timeout(5, TimeUnit.SECONDS)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    allOffersLiveData.value = it.offers
                }, {
                    errorLiveData.value = SingleEvent(it)
                })
        )
    }

    private fun filter(offers: List<OfferDTO>): List<OfferDTO> {
        return offers.filter { it.price.amount.toDouble() in priceFrom..priceTo }
    }

    fun applyFilter() {
        allOffersLiveData.value?.also {
            offersLiveData.value = filter(it)
        }
    }

    fun clear() {
        compositeDisposable.clear()
    }

    fun dispose() {
        compositeDisposable.dispose()
    }

}