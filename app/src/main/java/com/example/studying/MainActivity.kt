package com.example.studying

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        connectWebSocket()
    }

    override fun onStop() {
        super.onStop()
        disconnectWebSocket()
    }

    private var webSocketFile: WebSocket? = null
    private var webSocketText: WebSocket? = null

    private fun connectWebSocket() {
        val client = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .build()

        val requestFile = Request.Builder()
            .url("http://192.168.1.4:8080/file")
            .build()

        val requestText = Request.Builder()
            .url("http://192.168.1.4:8080/text")
            .build()

        binding.file.setOnClickListener {
            val file = File(cacheDir, "s14.9.png").readBytes()
            webSocketFile?.send(ByteString.of(*file))
        }

        binding.text.setOnClickListener {
            webSocketText?.send("Text")
        }

        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d("s14.9", "onOpen")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d("s14.9", "Сообщение: $text")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                val file = File(cacheDir, "s14.9.png")
                file.createNewFile()
                val fos = FileOutputStream(file)
                fos.use { output ->
                    output.write(bytes.toByteArray())
                }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                Log.d("s14.9", "onClosing")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d("s14.9", "onClosing")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d("s14.9", "onFailure ${t.message}")
            }
        }

        webSocketFile = client.newWebSocket(requestFile, listener)
        webSocketText = client.newWebSocket(requestText, listener)
    }

    private fun disconnectWebSocket() {
        webSocketFile?.close(1000, "User initiated disconnection")
        webSocketText?.close(1000, "User initiated disconnection")
    }
}