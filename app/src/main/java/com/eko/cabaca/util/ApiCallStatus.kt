package com.eko.cabaca.util


sealed class ApiCallStatus
object LOADING : ApiCallStatus()
data class SUCCESS(val response: Any) : ApiCallStatus()
data class ERROR(val message: String?) : ApiCallStatus()