package ch.ost.rj.mge.ostandfurious.objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ch.ost.rj.mge.ostandfurious.R;

public class Bike {

    Coord bottomLeft, bottomRight, topLeft, topRight;
    int width, height;
    Bitmap bike;

    public Bike(int screenX, int screenY, Resources res) {
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

    public Bitmap getBike() {
        return bike;
    }

    public int getHeight() {
        return height;
    }

    public Coord getBottomLeft() {
        return bottomLeft;
    }

    public Coord getBottomRight() {
        return bottomRight;
    }

    public Coord getTopLeft() {
        return topLeft;
    }

    public Coord getTopRight() {
        return topRight;
    }

    public void move(int amount) {
        bottomLeft.moveX(amount);
        bottomRight.moveX(amount);
        topLeft.moveX(amount);
        topRight.moveX(amount);
    }
}
