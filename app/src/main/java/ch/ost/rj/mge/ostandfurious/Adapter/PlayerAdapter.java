package ch.ost.rj.mge.ostandfurious.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.ost.rj.mge.ostandfurious.model.Player;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerViewHolder> {
    private List<Player> players;

    public PlayerAdapter() {
        this.players = new ArrayList<>();
    }

    public void updatePlayers(List<Player> players) {
        this.players = players;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(
                android.R.layout.simple_list_item_2,
                parent,
                false);

        TextView nameView = view.findViewById(android.R.id.text1);
        TextView scoreView = view.findViewById(android.R.id.text2);

        return new PlayerViewHolder(view, nameView, scoreView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = this.players.get(position);
        holder.nameView.setText(player.playerName);
        holder.scoreView.setText(String.valueOf(player.score));
    }

    @Override
    public int getItemCount() {
        return this.players.size();
    }
}
