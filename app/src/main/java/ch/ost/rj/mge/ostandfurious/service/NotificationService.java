package ch.ost.rj.mge.ostandfurious.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationService {
    private static final String CID = "OST and Furious Channel";
    private static final String CNAME = "O&F Notifications";
    private static final String CDESC = "Ein Channel für O&F";
    private static final int CIMP = NotificationManager.IMPORTANCE_HIGH;
    private static int notificationId = 1;

    public static void initialize(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel;
            channel = new NotificationChannel(CID, CNAME, CIMP);
            channel.setDescription(CDESC);
            manager.createNotificationChannel(channel);
        }
    }

    public static void createNotification(Context context, int icon, String title, String content) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        Notification notification;
        notification = new NotificationCompat.Builder(context, CID)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .build();
        manager.notify(notificationId++, notification);
    }
}
