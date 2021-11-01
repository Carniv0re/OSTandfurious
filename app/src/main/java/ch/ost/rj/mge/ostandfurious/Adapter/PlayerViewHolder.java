package ch.ost.rj.mge.ostandfurious.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PlayerViewHolder extends RecyclerView.ViewHolder {
    public TextView nameView;
    public TextView scoreView;

    public PlayerViewHolder(View parent, TextView nameView, TextView scoreView) {
        super(parent);
        this.nameView = nameView;
        this.scoreView = scoreView;
    }
}
