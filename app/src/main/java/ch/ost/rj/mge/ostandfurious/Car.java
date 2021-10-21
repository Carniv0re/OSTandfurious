package ch.ost.rj.mge.ostandfurious;

import static ch.ost.rj.mge.ostandfurious.GameView.screenRatioX;
import static ch.ost.rj.mge.ostandfurious.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Car {

    int x = 0, y, width, height, carCounter = 1;
    Bitmap car1, car2, car3, car4;

    Car(int screenX, int xCoord, Resources res) {
        car1 = BitmapFactory.decodeResource(res, R.drawable.car);
        car2 = BitmapFactory.decodeResource(res, R.drawable.car);
        car3 = BitmapFactory.decodeResource(res, R.drawable.car);
        car4 = BitmapFactory.decodeResource(res, R.drawable.car);

        width = car1.getWidth();
        height = car1.getHeight();

        width /= 4;
        height /= 4;
        //width = (int) (width * screenRatioX);
        //height = (int) (height * screenRatioY);

        car1 = Bitmap.createScaledBitmap(car1, width, height, false);

        y = -height;
        x = xCoord;
    }

    Bitmap getCar() {
        /*if(carCounter == 1) {
            carCounter++;
            return car1;
        }*/
        return car1;
    }
}
