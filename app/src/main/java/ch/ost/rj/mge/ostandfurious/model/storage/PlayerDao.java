package ch.ost.rj.mge.ostandfurious.model.storage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ch.ost.rj.mge.ostandfurious.model.Player;

@Dao
public interface PlayerDao {
    @Query("SELECT * FROM ranking")
    List<Player> getPlayers();

    @Query("DELETE FROM ranking")
    void nuke();

    @Insert
    void insert(Player player);

    @Update
    void update(Player player);
}
