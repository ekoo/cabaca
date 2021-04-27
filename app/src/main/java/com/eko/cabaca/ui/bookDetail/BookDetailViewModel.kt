package com.eko.cabaca.ui.bookDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eko.cabaca.model.BookDetailResponseModel
import com.eko.cabaca.network.BookServices
import com.eko.cabaca.network.BookServicesBuilder
import com.eko.cabaca.util.ApiCallStatus
import com.eko.cabaca.util.ERROR
import com.eko.cabaca.util.LOADING
import com.eko.cabaca.util.SUCCESS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookDetailViewModel : ViewModel() {

    private val bookServices: BookServices = BookServicesBuilder.build()
    private val _apiCallStatus = MutableLiveData<ApiCallStatus>()
    val apiCallStatus: LiveData<ApiCallStatus> get() = _apiCallStatus
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    fun getBooksById(bookId: Int){
        viewModelScope.launch(Dispatchers.IO){
            _apiCallStatus.postValue(LOADING)
            try {
                val result = bookServices.getBookById(bookId.toString())
                _apiCallStatus.postValue(SUCCESS(result))

                val writer = bookServices.getBooksByWriter(result.result.writerByWriterId.userByUserId.id)
                _name.postValue(writer.result.name)

            }catch (e: Exception){
                _apiCallStatus.postValue(ERROR(e.localizedMessage))
            }
        }
    }
}