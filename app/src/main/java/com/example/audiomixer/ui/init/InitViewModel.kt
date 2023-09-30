package com.example.audiomixer.ui.init

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InitViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    private val _online = MutableLiveData<Boolean>().apply {
        value = false
    }
    val online: LiveData<Boolean> = _online

    fun isOnline() {
        if (_online.value!!) _online.value = !_online.value!!
    }
}