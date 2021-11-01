package ch.ost.rj.mge.ostandfurious.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ch.ost.rj.mge.ostandfurious.R;
import ch.ost.rj.mge.ostandfurious.model.PlayerRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PlayerRepository.initialize(this);

        /*//This is for testing purposes, to get to the game screen faster. Remove later
        Intent gameActivityIntent = new Intent(
                this,
                gameActivity.class
        );
        startActivity(gameActivityIntent);
        //Test end*/

        Button playGameBtn = this.findViewById(R.id.buttonPlayGame);
        playGameBtn.setEnabled(false);

        Button leaderboardBtn = this.findViewById(R.id.buttonLeaderboard);
        leaderboardBtn.setOnClickListener(v -> {
            Intent leaderboardActivityIntent = new Intent(
                    this,
                    leaderboardActivity.class
            );
            startActivity(leaderboardActivityIntent);
        });

        Button creditsBtn = this.findViewById(R.id.buttonCredits);
        creditsBtn.setOnClickListener(v -> {
            Intent creditsActivityIntent = new Intent(
                    this,
                    creditActivity.class
            );
            startActivity(creditsActivityIntent);
        });

        EditText nameEditText = this.findViewById(R.id.editTextYourName);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String playerName = extras.getString("playerName");
            if (playerName != null) {
                nameEditText.setText(playerName);
            }
        }
        if (nameEditText.getText().equals("")) {
            nameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    playGameBtn.setEnabled(true);
                }
            });
        } else {
            playGameBtn.setEnabled(true);
        }

        playGameBtn.setOnClickListener(v -> {
            Intent tempGameActivityIntent = new Intent(
                    this,
                    gameActivity.class
            );
            tempGameActivityIntent.putExtra("playerName", nameEditText.getText().toString());
            startActivity(tempGameActivityIntent);
        });

        Button localeButton = (Button) findViewById(R.id.buttonLanguage);
        localeButton.setOnClickListener(v -> {
            localeButton.setText("DE");

        });
    }
}