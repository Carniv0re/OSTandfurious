package ch.ost.rj.mge.ostandfurious;

import static ch.ost.rj.mge.ostandfurious.GameView.screenRatioX;
import static ch.ost.rj.mge.ostandfurious.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bike {

    int x, y, width, height;
    Bitmap bike;

    Bike(int screenX, int screenY, Resources res) {
        bike = BitmapFactory.decodeResource(res, R.drawable.bike);
        if(bike == null) {
            System.out.println("Bike.java/18: Bike is null!");
        }
        //System.out.println("Bitmap height: "+ bike.getHeight());
        width = bike.getWidth();
        height = bike.getHeight();
        /*System.out.println("Height initial: " + height);
        System.out.println("Width initial: " + width);*/

        width /= 4;
        height /= 4;
        //System.out.println("Height after division: " + height);


        //width *= screenRatioX;
        //height *= screenRatioY;

        /*System.out.println("Width:" + width);
        System.out.println("Height: " + height);*/

        bike = Bitmap.createScaledBitmap(bike, width, height, false);

        if(bike == null) {
            System.out.println("Bike.java/32: Scaled Bike is null!");
        }

        x = screenX / 2 - width / 2;
        y = (int) (screenY * 0.75);
    }

    Bitmap getBike() {
        return bike;
    }
}
