package ch.ost.rj.mge.ostandfurious;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This is for testing purposes, to get to the game screen faster. Remove later
        Intent gameActivityIntent = new Intent(
                this,
                gameActivity.class
        );
        startActivity(gameActivityIntent);
        //Test end

        Button playGameBtn = this.findViewById(R.id.buttonPlayGame);
        playGameBtn.setEnabled(false);

        Button leaderboardBtn = this.findViewById(R.id.buttonLeaderboard);
        leaderboardBtn.setOnClickListener(v -> {
            Intent leaderboardActivityIntent = new Intent (
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

        /*playGameBtn.setOnClickListener(v -> {
            Intent gameActivityIntent = new Intent(
                    this,
                    gameActivity.class
                    );
            startActivity(gameActivityIntent);
        });*/
    }
}