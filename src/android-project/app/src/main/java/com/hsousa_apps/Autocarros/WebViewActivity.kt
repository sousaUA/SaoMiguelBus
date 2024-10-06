package com.hsousa_apps.Autocarros

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var btnBackToApp: Button
    private lateinit var btnClose: Button
    private lateinit var linearLayout: View // Reference to the LinearLayout

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetJavaScriptEnabled") // Suppress warning for enabling JavaScript
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webView = findViewById(R.id.webView)
        btnBackToApp = findViewById(R.id.btnBackToApp)
        btnClose = findViewById(R.id.btnClose)
        linearLayout = findViewById(R.id.buttonLayout) // Assuming you have an ID for the LinearLayout

        webView.webViewClient = WebViewClient() // Ensures links are opened within the WebView
        webView.settings.javaScriptEnabled = true // Enable JavaScript if needed
        CookieManager.getInstance().setAcceptCookie(true) // Allow the use of cookies
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)

        // Load the desired URL
        webView.loadUrl("https://saomiguelbus.com?device=android-app")

        // Set up the button click listener for Back to App
        btnBackToApp.setOnClickListener {
            navigateToMainActivity()
        }

        // Set up the button click listener for Close
        btnClose.setOnClickListener {
            closeLinearLayout()
        }
    }

    private fun closeLinearLayout() {
        linearLayout.visibility = View.GONE // Hide the LinearLayout
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