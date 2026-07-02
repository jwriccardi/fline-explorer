package org.fline.tribute

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val web = WebView(this)
        web.setBackgroundColor(0xFF050507.toInt())   // night black — no white flash on launch
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true        // localStorage
        web.webViewClient = WebViewClient()          // keep navigation inside the WebView
        setContentView(web)

        // The bundle is fully self-contained; device mode (bezel-free) is its default.
        web.loadUrl("file:///android_asset/fline.html")

        // Gallery-floor behavior: never sleep, own the whole panel.
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, web).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
