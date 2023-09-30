package com.example.audiomixer.requests

import android.util.Log
import com.example.audiomixer.model.RequestType
import com.example.audiomixer.model.ResponseType
import com.example.audiomixer.model.SmartRequest
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket

class QuickRequest {
    fun sendRequestToServerWithTimeout(serverIpAddress: String, serverPort: Int, request: String): String {
        try {
            val socket = Socket(serverIpAddress, serverPort)
            val outputStream: OutputStream = socket.getOutputStream()
            val inputStream = socket.getInputStream()
            val reader = BufferedReader(InputStreamReader(inputStream, Charsets.US_ASCII))

            // Отримуємо дані для відправлення
            val dataToSend = "${request}"

            // Відправляємо дані на сервер
            outputStream.write(dataToSend.toByteArray())
            outputStream.flush()
            Log.d("QuickRequest","Данні відправлено ${dataToSend}")

            // Отримуємо відповідь від сервера
            var response = ""
            val buffer = CharArray(1024)
            val bytesRead: Int

            while (true) {
                bytesRead = reader.read(buffer)
                String(buffer, 0, bytesRead).also { response = it}
                break
            }

            Log.d("QuickRequest","Данні отримано: ${response}")

            socket.close()
            return response

        } catch (e: Exception) {
            val json = Json {
                encodeDefaults = true
            }
            val updateRequest = SmartRequest()
            updateRequest.Type = RequestType.initRequest.value
            updateRequest.Data = ResponseType.failed.value

            val requestJson = json.encodeToString(updateRequest)

            Log.d("QuickRequest","Помилка: ${e.printStackTrace()}")
            return requestJson
        }
    }
}

