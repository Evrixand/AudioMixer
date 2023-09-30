package com.example.audiomixer.ui.commandCenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommandCenterViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Command Center Fragment"
    }
    val text: LiveData<String> = _text
}