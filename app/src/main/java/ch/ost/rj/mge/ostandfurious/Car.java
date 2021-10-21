package ch.ost.rj.mge.ostandfurious;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Car {

    Coord bottomLeft, bottomRight, topLeft, topRight;
    int x = 0, y, width, height;
    Bitmap car;

    Car(int screenX, int xCoord, Resources res) {
        car = BitmapFactory.decodeResource(res, R.drawable.car);

        width = car.getWidth();
        height = car.getHeight();

        width /= 2;
        height /= 2;
        //width = (int) (width * screenRatioX);
        //height = (int) (height * screenRatioY);

        car = Bitmap.createScaledBitmap(car, width, height, false);

        bottomLeft = new Coord(xCoord, 0);
        bottomRight = new Coord(xCoord + width, 0);
        topLeft = new Coord(xCoord, -height);
        topRight = new Coord(xCoord + width, -height);
    }

    Bitmap getCar() {
        return car;
    }

    public void move(int amount) {
        bottomLeft.moveY(amount);
        bottomRight.moveY(amount);
        topLeft.moveY(amount);
        topRight.moveY(amount);
    }

    private boolean inMiddleOf(Coord toCheck) {
        return toCheck.getX() >= bottomLeft.getX() && toCheck.getX() <= bottomRight.getX()
                && toCheck.getY() <= bottomLeft.getY() && toCheck.getY() >= topLeft.getY();
    }

    public boolean isCollidingWith(Bike bike) {
        if(inMiddleOf(bike.bottomLeft) || inMiddleOf(bike.bottomRight)
        || inMiddleOf(bike.topLeft) || inMiddleOf(bike.topRight)) {
            return true;
        }
        return false;
    }
}
