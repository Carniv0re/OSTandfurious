package ch.ost.rj.mge.ostandfurious.model;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

import ch.ost.rj.mge.ostandfurious.model.storage.PlayerDatabase;

public final class PlayerRepository {
    private static PlayerDatabase database;

    public static void initialize(Context context) {
        database = Room.databaseBuilder(context, PlayerDatabase.class, "ranking.db").allowMainThreadQueries().build();

        if (getPlayers().size() == 0) {
            addPlayer("Diego", "9999");
            addPlayer("Lucas", "10000");
        }
    }

    public static List<Player> getPlayers() {
        return database.playerDao().getPlayers();
    }

    //Only use to wipe Database
    public static void nukeDatabase() {
        database.playerDao().nuke();
    }

    public static void addPlayer(Player player) {
        database.playerDao().insert(player);
    }

    public static boolean addPlayer(String playerName, String score) {
        Player player = new Player();
        player.playerName = playerName;

        try {
            player.score = Integer.parseInt(score);
            System.out.println(player.score);
        } catch (NumberFormatException ex) {
            System.out.println("Could not convert score to an int!");
            player.score = -1;
        }

        for (Player p : getPlayers()) {
            if (p.playerName.equals(player.playerName)) {
                if (player.score > p.score) {
                    database.playerDao().update(player);
                    return true;
                }
                return false;
            }
        }

        addPlayer(player);
        return true;
    }
}
