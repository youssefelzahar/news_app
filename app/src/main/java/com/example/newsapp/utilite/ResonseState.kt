package com.example.newsapp.utilite

import android.provider.ContactsContract.RawContacts.Data


sealed class ResonseState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : ResonseState<T>(data)
    class Error<T>(message: String, data: T? = null) : ResonseState<T>(data, message)
    class Load<T> : ResonseState<T>()
}