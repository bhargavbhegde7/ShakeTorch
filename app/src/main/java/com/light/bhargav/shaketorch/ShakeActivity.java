package com.light.bhargav.shaketorch;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShakeActivity extends Activity implements SensorEventListener {

    private TextView dataView;
    private TextView negativeLimted;
    private TextView negative;
    private SensorManager sManager;

    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    private Camera.Parameters params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        negativeLimted = (TextView)findViewById(R.id.negativeLimited);
        negative = (TextView)findViewById(R.id.negative);
        dataView = (TextView) findViewById(R.id.dataView);
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);

    }

    @Override
    protected void onStop() {
        sManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //if sensor is unreliable, return void
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            return;
        }

        dataView.setText(Float.toString(event.values[2]) + "\n" +
                Float.toString(event.values[1]) + "\n" +
                Float.toString(event.values[0]));

        if(event.values[2]<-35) {
            System.out.println("<-35 detected");
            negativeLimted.setText(Float.toString(event.values[2]));
            getCamera();
            turnOnFlash();
            //SystemClock.sleep(2000);
        }

        if(event.values[2]<0) {
            negative.setText(Float.toString(event.values[2]));
        }
    }

    // getting camera parameters
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Toast.makeText(getApplicationContext(),"Camra open error",Toast.LENGTH_LONG);
            }
        }
        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
            // device doesn't support flash
            Toast.makeText(getApplicationContext(), "No flash support", Toast.LENGTH_LONG);
        }
    }

    public void turnOnFlash(){
        //if flash is off
        if (!isFlashOn) {
            System.out.println("switching on the flash");
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;

            //SystemClock.sleep(5000);
        }
        /*else{
            System.out.println("switching off the flash");
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void startService(View view){
        EditText input = (EditText) findViewById(R.id.serviceText);
        String strInputMsg = input.getText().toString();
        Intent msgIntent = new Intent(this, MyService.class);
        msgIntent.putExtra(MyService.PARAM_IN_MSG, strInputMsg);
        startService(msgIntent);
    }
}
