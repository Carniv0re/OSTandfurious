package ch.ost.rj.mge.ostandfurious.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ch.ost.rj.mge.ostandfurious.R;

public class CreditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        getSupportActionBar().hide();

        Button backBtn = this.findViewById(R.id.buttonBack);
        backBtn.setOnClickListener(view -> {
            Intent mainActivityIntent = new Intent(
                    this,
                    MainActivity.class
            );
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainActivityIntent);
        });
    }
}