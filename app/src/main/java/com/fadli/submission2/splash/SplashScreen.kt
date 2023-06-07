package com.fadli.submission2.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.fadli.submission2.databinding.ActivitySplashScreenBinding
import com.fadli.submission2.ui.home.MainActivity
import com.fadli.submission2.util.Constanta.TIME_SPLASH


class SplashScreen : AppCompatActivity() {

    private lateinit var splashBinding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val i = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(i)
                finish()
            }, TIME_SPLASH
        )
    }
}