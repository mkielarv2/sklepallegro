package com.mkielar.sklepallegro.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkielar.sklepallegro.AllegroApiClient
import com.mkielar.sklepallegro.model.OffersDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val allegroApiClient: AllegroApiClient) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val offersLiveData: MutableLiveData<OffersDTO> = MutableLiveData()
    val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()

    init {
        fetch()
    }

    fun fetch() = allegroApiClient.getData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            offersLiveData.value = it
        }, {
            errorLiveData.value = it
        })
}