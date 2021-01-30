package com.hlccd.cloud

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timerTask

class Cover : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cover)
        val timer = Timer()
        timer.schedule(timerTask {
            startActivity(intent)
            finish()
        }, 3000)
    }
}
