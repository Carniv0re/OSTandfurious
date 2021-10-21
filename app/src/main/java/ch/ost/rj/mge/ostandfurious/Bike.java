package ch.ost.rj.mge.ostandfurious;

import static ch.ost.rj.mge.ostandfurious.GameView.screenRatioX;
import static ch.ost.rj.mge.ostandfurious.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bike {

    Coord bottomLeft, bottomRight, topLeft, topRight;
    int width, height;
    Bitmap bike;

    Bike(int screenX, int screenY, Resources res) {
        bike = BitmapFactory.decodeResource(res, R.drawable.bike);
        if(bike == null) {
            System.out.println("Bike.java/18: Bike is null!");
        }
        width = bike.getWidth();
        height = bike.getHeight();

        width /= 4;
        height /= 4;


        //width *= screenRatioX;
        //height *= screenRatioY;

        /*System.out.println("Width:" + width);
        System.out.println("Height: " + height);*/

        bike = Bitmap.createScaledBitmap(bike, width, height, false);

        if(bike == null) {
            System.out.println("Bike.java/32: Scaled Bike is null!");
        }

        topLeft = new Coord(screenX / 2 - width / 2, (int) (screenY * 0.75));
        topRight = new Coord(screenX / 2 + width / 2, (int) (screenY * 0.75));

        bottomRight = new Coord(screenX / 2 + width / 2, (int) (screenY * 0.75 + height));
        bottomLeft = new Coord(screenX / 2 - width / 2, (int) (screenY * 0.75 + height));
    }

    Bitmap getBike() {
        return bike;
    }

    public void moveBike(int amount) {
        bottomLeft.moveX(amount);
        bottomRight.moveX(amount);
        topLeft.moveX(amount);
        topRight.moveX(amount);
    }
}
