package ch.ost.rj.mge.ostandfurious;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

public class EndScreenActivity extends AppCompatActivity {

    private String playerName, meters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endscreen);
        getSupportActionBar().hide();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * .8));

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            System.out.println("Extras are null!");
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        }
        else {
            playerName = extras.getString("playerName");
            if(playerName == null) {
                System.out.println("Name is null!");
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
            }
            meters = extras.getString("meters");
            if(meters == null) {
                System.out.println("Meters is null!");
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
            }
        }

        TextView playerNameTextView = (TextView) findViewById(R.id.playerNameTextView);
        playerNameTextView.setText(playerName);
        TextView metersTextView = (TextView) findViewById(R.id.metersTextView);
        metersTextView.setText(meters);

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        Button backToMenuButton = (Button) findViewById(R.id.backToMenuButton);

        backToMenuButton.setOnClickListener(v -> {
            Intent mainMenuActivity = new Intent(
                    this,
                    MainActivity.class
            );
            mainMenuActivity.putExtra("playerName", playerName);
            mainMenuActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainMenuActivity);
        });
        playAgainButton.setOnClickListener(v -> {
            Intent gameActivity = new Intent(
                    this,
                    gameActivity.class
            );
            gameActivity.putExtra("playerName", playerName);
            gameActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(gameActivity);
        });

    }
}
