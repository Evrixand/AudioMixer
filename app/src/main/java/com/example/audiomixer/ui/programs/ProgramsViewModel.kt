package com.example.audiomixer.ui.programs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProgramsViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Programs Fragment"
    }
    val text: LiveData<String> = _text
}