package com.eko.cabaca.ui.latestBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eko.cabaca.network.BookServices
import com.eko.cabaca.network.BookServicesBuilder
import com.eko.cabaca.util.ApiCallStatus
import com.eko.cabaca.util.ERROR
import com.eko.cabaca.util.LOADING
import com.eko.cabaca.util.SUCCESS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LatestBookViewModel : ViewModel() {

    private val bookServices: BookServices = BookServicesBuilder.build()
    private val _apiCallStatus = MutableLiveData<ApiCallStatus>()
    val apiCallStatus: LiveData<ApiCallStatus> get() = _apiCallStatus

    private fun getLatestBooks(){
        viewModelScope.launch(Dispatchers.IO){
            _apiCallStatus.postValue(LOADING)
            try {
                val result = bookServices.getLatestBooks()
                _apiCallStatus.postValue(SUCCESS(result))
            }catch (e: Exception){
                _apiCallStatus.postValue(ERROR(e.localizedMessage))
            }
        }
    }

    init {
        getLatestBooks()
    }
}