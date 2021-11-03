package ch.ost.rj.mge.ostandfurious.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.room.Room;

import ch.ost.rj.mge.ostandfurious.R;
import ch.ost.rj.mge.ostandfurious.model.PlayerRepository;
import java.util.Locale;

import ch.ost.rj.mge.ostandfurious.R;
import ch.ost.rj.mge.ostandfurious.util.ContextUtils;

public class MainActivity extends AppCompatActivity {

    private String locale;
    private String[] locales = {"en", "de", "ja"};
    private int currentLocaleIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PlayerRepository.initialize(this);
        getSupportActionBar().hide();

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
                    LeaderboardActivity.class
            );
            leaderboardActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(leaderboardActivityIntent);
        });

        Button creditsBtn = this.findViewById(R.id.buttonCredits);
        creditsBtn.setOnClickListener(v -> {
            Intent creditsActivityIntent = new Intent(
                    this,
                    CreditActivity.class
            );
            creditsActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        if (nameEditText.getText().toString().equals("")) {
            nameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(nameEditText.getText().toString().length() < 1 || nameEditText.getText().toString().length() > 10) {
                        playGameBtn.setEnabled(false);
                        nameEditText.setError(getString(R.string.error_message));
                    } else {
                        nameEditText.setError(null);
                        playGameBtn.setEnabled(true);
                    }
                }
            });
        } else {
            playGameBtn.setEnabled(true);
        }

        playGameBtn.setOnClickListener(v -> {
            Intent tempGameActivityIntent = new Intent(
                    this,
                    GameActivity.class
            );
            tempGameActivityIntent.putExtra("playerName", nameEditText.getText().toString());
            startActivity(tempGameActivityIntent);
        });

        Button localeButton = (Button) findViewById(R.id.buttonLanguage);
        localeButton.setText(locale.toUpperCase());
        localeButton.setOnClickListener(v -> {
            int newIndex = (currentLocaleIndex + 1) % locales.length;
            String newText = locales[newIndex];
            SharedPreferences sp = getApplicationContext().getSharedPreferences("UserSettings", 0);
            SharedPreferences.Editor editor = sp.edit();

            editor.putInt("localeIndex", newIndex);
            editor.putString("preferredLocale", newText);
            editor.commit();
            Intent mainMenuActivity = new Intent(
                    this,
                    MainActivity.class
            );
            mainMenuActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainMenuActivity);
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences sp = newBase.getSharedPreferences("UserSettings", 0);
        String localeSetting = sp.getString("preferredLocale", "en");
        currentLocaleIndex = sp.getInt("localeIndex", 0);
        locale = localeSetting;
        Locale localeToSwitchTo = new Locale(localeSetting);
        ContextWrapper localeUpdatedContext = ContextUtils.updateLocale(newBase, localeToSwitchTo);

        super.attachBaseContext(localeUpdatedContext);
    }

}