package com.example.audiomixer.model

enum class RequestType (val value: String) {
    update("update"),
    initRequest("initRequest"),
    volume("volume"),
    masterVolume("masterVolume")
}