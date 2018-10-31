package com.frankchang.proximitysensor_demo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView show;
    private SensorManager managers;
    private Sensor sensor;
    private sensorListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show = findViewById(R.id.tvShow);

        // 取得傳感器物件
        managers = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (managers != null) {
            sensor = managers.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 建立監聽器
        if (sensor != null) {
            listener = new sensorListener();
            // 註冊
            managers.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 註銷
        managers.unregisterListener(listener);
    }


    // 監聽器
    private class sensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;

            String proximity = "Value : " + values[0];
            show.setText(proximity);
            show.append("Max Value : " + sensor.getMaximumRange());

            if (values[0] < 1) {
                show.append("接近！");

            } else {
                show.append("離開！");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // 未使用
        }
    }

}
