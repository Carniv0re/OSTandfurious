package ch.ost.rj.mge.ostandfurious.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ranking")
public final class Player {
    @PrimaryKey @NonNull
    public String playerName;

    @ColumnInfo
    public int score;
}
