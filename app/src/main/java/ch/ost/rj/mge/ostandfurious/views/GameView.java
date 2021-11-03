package ch.ost.rj.mge.ostandfurious.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import ch.ost.rj.mge.ostandfurious.R;
import ch.ost.rj.mge.ostandfurious.activities.EndScreenActivity;
import ch.ost.rj.mge.ostandfurious.objects.Bike;
import ch.ost.rj.mge.ostandfurious.objects.Car;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private Context context;
    public static boolean isPlaying, isGameOver = false;
    private int screenX, screenY, distanceSinceLastCar;
    public static float screenRatioX, screenRatioY;
    public static boolean isDrawingCars = false;
    private double backGroundSpeed;
    private int laps = 0;
    public static int meters = 0;
    private boolean speedIncrease = true;
    private String playerName;
    private Bike bike;
    private List<Car> cars = new ArrayList<>();
    private Paint paint, textPaint;
    private GameBackground background1, background2;

    public GameView(Context context, int screenX, int screenY, String playerName) {
        super(context);
        this.context = context;

        this.screenX = screenX;
        this.screenY = screenY;

        this.playerName = playerName;

        distanceSinceLastCar = 0;

        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        paint = new Paint();
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(200);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        background1 = new GameBackground(screenX, screenY, getResources());
        background2 = new GameBackground(screenX, screenY, getResources());

        backGroundSpeed = 1d;
        laps = 0;

        bike = new Bike(screenX, screenY, getResources());

        background2.setY(0 - background2.getBackground().getHeight());
    }

    @Override
    public void run() {

        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        List<Car> trash = new ArrayList<>();

        if (isGameOver) {
            pause();
        }
        double currentSpeed = 10 * backGroundSpeed;
        background1.move((int) currentSpeed);
        background2.move((int) currentSpeed);
        meters += currentSpeed / 10;
        if (cars.size() == 0) {
            Car firstCar = new Car(screenX, screenX / 2, getResources());
            cars.add(firstCar);
        }
        if (distanceSinceLastCar >= bike.getHeight() * 5) {
            spawnCar();
            distanceSinceLastCar = 0;
        }
        distanceSinceLastCar += currentSpeed;


        if (background1.getY() > screenY) {
            background1.setY(0 - background1.getBackground().getHeight());
            laps++;
        }
        if (background2.getY() > screenY) {
            background2.setY(0 - background1.getBackground().getHeight());
            laps++;
        }
        if (laps % 2 == 0 && laps > 0 && speedIncrease) {
            this.backGroundSpeed += 0.1 * laps;
            speedIncrease = false;
            System.out.println("Speed: " + this.backGroundSpeed);
        } else if (laps % 2 == 1) {
            speedIncrease = true;
        }
        for (Car car : cars) {
            car.move((int) currentSpeed);
            if (car.getTopLeft().getY() >= screenY) {
                trash.add(car);
            }
        }

        for (Car car : trash) {
            cars.remove(car);
        }
    }

    private void draw() {

        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.getBackground(), 0, background1.getY(), paint);
            canvas.drawBitmap(background2.getBackground(), 0, background2.getY(), paint);

            canvas.drawBitmap(bike.getBike(), bike.getTopLeft().getX(), bike.getTopLeft().getY(), paint);

            isDrawingCars = true;
            for (Car car : cars) {
                canvas.drawBitmap(car.getCar(), car.getTopLeft().getX(), car.getTopLeft().getY(), paint);

                if (car.isCollidingWith(bike)) {
                    isGameOver = true;
                    MediaPlayer mpCrash = MediaPlayer.create(context, R.raw.crash);
                    mpCrash.start();

                }
            }
            isDrawingCars = false;

            Rect textBounds = new Rect();
            textPaint.getTextBounds("y", 0, 1, textBounds);
            int metersY = textBounds.height();
            canvas.drawText(Integer.toString(meters), 0, metersY, textPaint);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;

        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        isPlaying = false;
        if (isGameOver) {
            Intent endScreenActivity = new Intent(
                    context,
                    EndScreenActivity.class
            );
            endScreenActivity.putExtra("playerName", playerName);
            endScreenActivity.putExtra("meters", Integer.toString(meters));
            context.startActivity(endScreenActivity);
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void moveBike(int amount) {
        amount *= screenRatioX;
        if (this.bike.getBottomLeft().getX() - amount < 0) {
            if (amount > 0) {
                this.bike.move(amount);
                /*this.bike.getBottomLeft().moveX(amount);
                this.bike.getBottomRight().moveX(amount);
                this.bike.getTopLeft().moveX(amount);
                this.bike.getTopRight().moveX(amount);*/
            }
        } else if (this.bike.getBottomRight().getX() + amount > screenX) {
            if (amount < 0) {
                this.bike.move(amount);
                /*this.bike.getBottomLeft().moveX(amount);
                this.bike.getBottomRight().moveX(amount);
                this.bike.getTopLeft().moveX(amount);
                this.bike.getTopRight().moveX(amount);*/
            }
        } else {
            this.bike.move(amount);
            /*this.bike.getBottomLeft().moveX(amount);
            this.bike.getBottomRight().moveX(amount);
            this.bike.getTopLeft().moveX(amount);
            this.bike.getTopRight().moveX(amount);*/
        }
    }

    public void spawnCar() {
        int possibleCoords[] = {0, (int) (screenX * 0.25), (int) (screenX / 2), (int) (screenX * 0.75)};
        Car newCar = new Car(screenX, possibleCoords[ThreadLocalRandom.current().nextInt(0, 4)], getResources());
        //System.out.println("New car spawned with width: " + newCar.width + " and height: " + newCar.height);
        //if(!isDrawingCars) {
        cars.add(newCar);
        //}
    }
}
