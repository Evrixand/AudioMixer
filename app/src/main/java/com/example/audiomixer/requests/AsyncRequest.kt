package com.example.audiomixer.requests

import android.os.AsyncTask
import android.util.Log
import com.example.audiomixer.model.RequestType
import com.example.audiomixer.model.ResponseType
import com.example.audiomixer.model.SmartRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket

@Suppress("DEPRECATION")
class AsyncRequest : AsyncTask<String?, Int?, String>() {

    override fun doInBackground(vararg params: String?): String {

        val serverIpAddress = params[0] // IP-адреса комп'ютера, де запущений сервер
        val serverPort = params[1]!!.toInt() // Порт, на якому слухає сервер

        try {
            val socket = Socket(serverIpAddress, serverPort)
            val outputStream: OutputStream = socket.getOutputStream()
            val inputStream = socket.getInputStream()
            val reader = BufferedReader(InputStreamReader(inputStream, Charsets.US_ASCII))

            // Отримуємо дані для відправлення
            val dataToSend = "${params[2]}"

            // Відправляємо дані на сервер
            println("Данні відправлено ${dataToSend}")
            outputStream.write(dataToSend.toByteArray())
            outputStream.flush()
            Log.d("AsyncRequest","Данні відправлено ${dataToSend}")

            // Отримуємо відповідь від сервера
            var response = ""
            val buffer = CharArray(1024)
            val bytesRead: Int

            while (true) {
                bytesRead = reader.read(buffer)
                String(buffer, 0, bytesRead).also { response = it}
                break
            }
            Log.d("AsyncRequest","Данні отримано: ${response}")

            // Закриваємо з'єднання
            socket.close()
            return response

        }
        catch (e: Exception) {

            val json = Json {
                encodeDefaults = true
            }
            val updateRequest = SmartRequest()
            updateRequest.Type = RequestType.initRequest.value
            updateRequest.Data = ResponseType.failed.value

            val requestJson = json.encodeToString(updateRequest)

            Log.d("AsyncRequest","Помилка: ${e.printStackTrace()}")

            return requestJson
        }
    }
    override fun onPostExecute(s: String) {
        super.onPostExecute(s)
    }
}
