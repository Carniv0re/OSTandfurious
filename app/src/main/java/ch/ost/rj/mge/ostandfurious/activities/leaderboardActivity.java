package ch.ost.rj.mge.ostandfurious.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ch.ost.rj.mge.ostandfurious.Adapter.PlayerAdapter;
import ch.ost.rj.mge.ostandfurious.R;
import ch.ost.rj.mge.ostandfurious.model.Player;
import ch.ost.rj.mge.ostandfurious.model.PlayerRepository;

public class leaderboardActivity extends AppCompatActivity {

    private PlayerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        Button backBtn = this.findViewById(R.id.buttonBack);
        backBtn.setOnClickListener(view -> {
            Intent mainActivityIntent = new Intent(
                    this,
                    MainActivity.class
            );
            startActivity(mainActivityIntent);
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new PlayerAdapter();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        RecyclerView recyclerView = findViewById(R.id.leaderboardRanking);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Player> players = PlayerRepository.getPlayers();
        Collections.sort(players, (lhs, rhs) -> Integer.compare(rhs.score, lhs.score));
        adapter.updatePlayers(players);
    }
}