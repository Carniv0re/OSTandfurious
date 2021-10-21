package ch.ost.rj.mge.ostandfurious;

import static ch.ost.rj.mge.ostandfurious.GameView.drawingCars;
import static ch.ost.rj.mge.ostandfurious.GameView.isPlaying;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class gameActivity extends AppCompatActivity {

    private GameView gameView;
    private SensorManager sm;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroScopeEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(gyroscopeSensor == null) {
            Toast.makeText(this, "This device has no Gyroscope!", Toast.LENGTH_SHORT);
        }

        gyroScopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[1] > 0.2f) {
                    gameView.moveBike(sensorEvent.values[1]);
                }
                else if(sensorEvent.values[1] < -0.2f) {
                    gameView.moveBike(sensorEvent.values[1]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView(this, point.x, point.y);
        setContentView(gameView);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(!drawingCars) {
                    if(isPlaying) {
                        gameView.spawnCar();
                    }
                }
            }
        }, 0, 1000);

        /*TextView countDownView = this.findViewById(R.id.countDownView);
        countDownView.setText("Ready?");
        countDownView.setVisibility(View.VISIBLE);

        new CountDownTimer(5000, 1000) {
            int count = 4;
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished > 4001) {
                    return;
                }
                if(count > 1){
                    countDownView.setText(Integer.toString(count - 1));
                    count--;
                }
                else {
                    countDownView.setText("GO!");

                }
            }

            public void onFinish() {
                countDownView.setEnabled(false);
                countDownView.setVisibility(View.GONE);
            }

        }.start();*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(gyroScopeEventListener);
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(gyroScopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
        gameView.resume();
    }
}