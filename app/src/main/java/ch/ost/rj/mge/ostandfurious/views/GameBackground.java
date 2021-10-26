package ch.ost.rj.mge.ostandfurious.views;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ch.ost.rj.mge.ostandfurious.R;

public class GameBackground {
    private int x = 0, y = 0;
    private Bitmap background;

    GameBackground(int screenX, int screenY, Resources res) {
        background = BitmapFactory.decodeResource(res, R.drawable.background);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap getBackground() {
        return background;
    }

    public void move(int amount) {
        this.y += amount;
    }
}
