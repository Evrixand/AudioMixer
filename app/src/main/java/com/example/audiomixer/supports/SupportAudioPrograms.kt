package com.example.audiomixer.supports

import com.example.audiomixer.model.AudioProgram

object SupportAudioPrograms {

    private val _listAudioPrograms = arrayListOf<AudioProgram>()

    fun getListAudioPrograms() = _listAudioPrograms

    fun fillList(list: List<AudioProgram>){
        _listAudioPrograms.clear()
        for (item in list){
            _listAudioPrograms.add(item)
        }
    }

}