package ch.ost.rj.mge.ostandfurious.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ch.ost.rj.mge.ostandfurious.R;

public class creditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        Button backBtn = this.findViewById(R.id.buttonBack);
        backBtn.setOnClickListener(view -> {
            Intent mainActivityIntent = new Intent(
                    this,
                    MainActivity.class
            );
            startActivity(mainActivityIntent);
        });
    }
}