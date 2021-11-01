package ch.ost.rj.mge.ostandfurious.model.storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ch.ost.rj.mge.ostandfurious.model.Player;

@Database(entities = {Player.class}, version = 1, exportSchema = false)
public abstract class PlayerDatabase extends RoomDatabase {
    public abstract PlayerDao playerDao();
}
