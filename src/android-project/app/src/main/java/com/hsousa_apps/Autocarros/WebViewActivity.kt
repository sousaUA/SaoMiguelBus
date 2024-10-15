package com.hsousa_apps.Autocarros

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
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

        // Initialize views
        webView = findViewById(R.id.webView)
        btnBackToApp = findViewById(R.id.btnBackToApp)
        btnClose = findViewById(R.id.btnClose)
        linearLayout = findViewById(R.id.buttonLayout) // Assuming you have an ID for the LinearLayout
        linearLayout.visibility = View.GONE

        setupWebView()

        // Set up button click listeners
        btnBackToApp.setOnClickListener { navigateToMainActivity() }
        btnClose.setOnClickListener { closeLinearLayout() }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                // Check if the URL is not null
                if (url != null) {
                    // Create an Intent to open the URL in a browser
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent) // Start the activity to open the link in the browser
                    return true // Indicate that we've handled the URL
                }
                return false // Let the WebView handle other URLs (if any)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)

                // Show a generic error message if needed
                Toast.makeText(this@WebViewActivity, "Error loading page", Toast.LENGTH_SHORT).show()
            }
        }

        val webSettings = webView.settings

        // General settings
        webSettings.javaScriptEnabled = true // Enable JavaScript
        webSettings.domStorageEnabled = true // Enable DOM storage
        webSettings.databaseEnabled = true // Enable HTML5 database storage
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT // Use default caching
        webSettings.loadsImagesAutomatically = true // Ensure images load automatically
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW // Allow mixed content (HTTP/HTTPS)

        // Enable file access
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true

        // Additional settings to enhance compatibility
        webSettings.useWideViewPort = true // Use viewport meta tag for scaling
        webSettings.loadWithOverviewMode = true // Adjust the WebView to fit the screen
        webSettings.setSupportZoom(true) // Enable zoom controls
        webSettings.builtInZoomControls = true // Enable built-in zoom controls
        webSettings.displayZoomControls = false // Hide the zoom controls on the screen

        // Enable cookies
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(webView, true)

        // Enable debugging for WebView
        WebView.setWebContentsDebuggingEnabled(true)

        // Load the desired URL
        webView.loadUrl("https://saomiguelbus.com?device=android-app")
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