package com.hlccd.cloud

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Main : AppCompatActivity(), SensorEventListener {
    private var cdt:MyCountDownTimer?=null
    private var canStart=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        val text:TextView = findViewById(R.id.text)
        val start: Button = findViewById(R.id.start)
        val stop: Button = findViewById(R.id.stop)
        val t=10L
        cdt = MyCountDownTimer(t*1000,1000, text)
        start.setOnClickListener{
            cdt!!.start()
        }
        stop.setOnClickListener{
            cdt!!.onFinish()
        }
        var sManager = getSystemService(SENSOR_SERVICE) as SensorManager
        var mSensorOrientation = sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        sManager.registerListener(this, mSensorOrientation, SensorManager.SENSOR_DELAY_UI)
    }
    override fun onSensorChanged(event: SensorEvent) {
        if (Math.round(event.values[1] * 100).toFloat() / 100 < -120 || Math.round(event.values[1] * 100).toFloat() / 100 > 120) {
            if (canStart){
                cdt!!.start()
                canStart=false
            }
        } else {
            if (!canStart){
                cdt!!.onFinish()
                canStart=true
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    private inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long, internal var tv: TextView) : CountDownTimer(millisInFuture, countDownInterval) {
        override fun onFinish() {
//            tv.setText("结束")
            cancel()
        }
        override fun onTick(millisUntilFinished: Long) {
            val m=millisUntilFinished/1000/60
            val s=millisUntilFinished/1000%60
            tv.setText(m.toString()+"："+s.toString())
        }
    }
}
