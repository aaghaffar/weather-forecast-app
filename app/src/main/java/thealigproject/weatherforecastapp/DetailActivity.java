package thealigproject.weatherforecastapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String savedExtra = getIntent().getStringExtra("humidity");
        TextView humidityText = findViewById(R.id.humidityTV);
        humidityText.setText(savedExtra);
        String savedExtra2 = getIntent().getStringExtra("summary");
        TextView summaryText = findViewById(R.id.summaryTV);
        summaryText.setText(savedExtra2);

    }
}
