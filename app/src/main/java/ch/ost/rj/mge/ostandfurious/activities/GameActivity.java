package ch.ost.rj.mge.ostandfurious.activities;

import static ch.ost.rj.mge.ostandfurious.views.GameView.isGameOver;
import static ch.ost.rj.mge.ostandfurious.views.GameView.meters;
import static ch.ost.rj.mge.ostandfurious.views.GameView.screenRatioX;

import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ch.ost.rj.mge.ostandfurious.views.GameView;
import ch.ost.rj.mge.ostandfurious.R;

public class GameActivity extends AppCompatActivity {

    public static String playerName;
    private GameView gameView;
    private SensorManager sm;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroScopeEventListener;
    private MediaPlayer mpEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            System.out.println("Extras are null!");
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        } else {
            playerName = extras.getString("playerName");
            if (playerName == null) {
                System.out.println("Name is null!");
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
            }
        }

        mpEngine  = MediaPlayer.create(this, R.raw.engine);
        mpEngine.setLooping(true);
        mpEngine.start();

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (gyroscopeSensor == null) {
            Toast.makeText(this, "This device has no Gyroscope!", Toast.LENGTH_SHORT).show();
        }

        gyroScopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.values[1] > 0.2f) {
                    gameView.moveBike((int) (sensorEvent.values[1] * screenRatioX * 2));
                } else if (sensorEvent.values[1] < -0.2f) {
                    gameView.moveBike((int) (sensorEvent.values[1] * screenRatioX * 2));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        isGameOver = false;
        meters = 0;
        gameView = new GameView(this, point.x, point.y, playerName);
        setContentView(gameView);


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
        mpEngine.stop();
        System.out.println("Paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(gyroScopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
        mpEngine.start();
        gameView.resume();
    }
}