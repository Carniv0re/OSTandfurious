package ch.ost.rj.mge.ostandfurious;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameView extends SurfaceView implements Runnable{

    private Thread thread;
    public static boolean isPlaying;
    private int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    public static boolean drawingCars = false;
    private double backGroundSpeed;
    private int laps = 0;
    private boolean speedIncrease = true;
    private Bike bike;
    private List<Car> cars = new ArrayList<>();
    private Paint paint;
    private GameBackground background1, background2;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        paint = new Paint();

        background1 = new GameBackground(screenX, screenY, getResources());
        background2 = new GameBackground(screenX, screenY, getResources());

        backGroundSpeed = 1d;
        laps = 0;

        bike = new Bike(screenX, screenY, getResources());

        background2.y = 0 - background2.background.getHeight();
        //background2.x = screenX;
    }

    @Override
    public void run() {

        while(isPlaying) {

            update();
            draw();
            sleep();
        }
    }

    private void update() {

        /* Horizontal movement
        background1.x -= 10;// * screenRatioX;
        background2.x -= 10;// * screenRatioX;

        if(background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
            System.out.println("1");
        }
        if(background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
            System.out.println("2");
        }*/
        double currentSpeed = 10 * backGroundSpeed;
        background1.y += currentSpeed;
        background2.y += currentSpeed;

        if(background1.y > screenY) {
            background1.y = 0 - background1.background.getHeight();
            laps++;
        }
        if(background2.y > screenY) {
            background2.y = 0 - background2.background.getHeight();
            laps++;
        }
        if(laps % 2 == 0 && laps > 0 && speedIncrease) {
            this.backGroundSpeed += 0.1;
            speedIncrease = false;
            System.out.println("Speed: " + this.backGroundSpeed);
        }
        else if(laps % 2 == 1){
            speedIncrease = true;
        }
        for (Car car : cars) {
            car.y += currentSpeed;
            if(car.y >= bike.y) {
                pause();
            }
        }
    }

    private void draw() {

        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            canvas.drawBitmap(bike.getBike(), bike.x, bike.y, paint);
            drawingCars = true;
            for(Car car : cars) {
                canvas.drawBitmap(car.getCar(), car.x, car.y, paint);
            }
            drawingCars = false;
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

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void moveBike(float amount) {
        /*if(this.bike.x - amount >= 0 && this.bike.x + amount <= screenX - this.bike.width) {
            this.bike.x += amount;
        }*/
        amount *= screenRatioX;
        //System.out.println(amount);
        if(this.bike.x - amount < 0) {
            if(amount > 0) {
                this.bike.x += amount;
            }
        }
        else if(this.bike.x + amount > screenX - this.bike.width) {
            if(amount < 0) {
                this.bike.x += amount;
            }
        }
        else {this.bike.x += amount;}
    }

    public void spawnCar() {
        int possibleCoords[] = {0, (int) (screenX * 0.25), (int) (screenX / 2), (int) (screenX * 0.75)};
        Car newCar = new Car(screenX, possibleCoords[ThreadLocalRandom.current().nextInt(0, 4)], getResources());
        System.out.println("New car spawned with width: " + newCar.width + " and height: " + newCar.height);
        cars.add(newCar);
    }
}
