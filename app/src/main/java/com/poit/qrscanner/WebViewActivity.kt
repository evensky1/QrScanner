package com.poit.qrscanner

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var currentLocation: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webView = findViewById<WebView?>(R.id.webView).apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
        }
        webView.loadUrl(intent.getStringExtra("url") ?: "https://www.google.com")

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivityIfNeeded(intent, 0)
                this@WebViewActivity.finish()
            }
        })

    }
}