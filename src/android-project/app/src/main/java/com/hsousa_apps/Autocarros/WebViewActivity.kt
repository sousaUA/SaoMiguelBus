package com.hsousa_apps.Autocarros

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var btnBackToApp: Button

    @SuppressLint("SetJavaScriptEnabled") // Suppress warning for enabling JavaScript
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webView = findViewById(R.id.webView)
        btnBackToApp = findViewById(R.id.btnBackToApp)

        webView.webViewClient = WebViewClient() // Ensures links are opened within the WebView
        webView.settings.javaScriptEnabled = true // Enable JavaScript if needed

        // Load the desired URL
        webView.loadUrl("https://saomiguelbus.com")

        // Set up the button click listener
        btnBackToApp.setOnClickListener {
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        // Clear the activity stack to prevent multiple instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        // Add an extra to indicate the navigation is from WebViewActivity
        intent.putExtra("fromWebView", true)
        startActivity(intent)
        finish() // Close WebViewActivity to remove it from the back stack
    }

    // Handle back button navigation within the WebView
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}