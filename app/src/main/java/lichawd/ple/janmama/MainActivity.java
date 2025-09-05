package lichawd.ple.janmama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputHours, inputMinutes;
    private Button btnConvert, btnShare, btnRestart;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        inputHours = findViewById(R.id.inputHours);
        inputMinutes = findViewById(R.id.inputMinutes);
        btnConvert = findViewById(R.id.btnConvert);
        btnShare = findViewById(R.id.btnShare);
        btnRestart = findViewById(R.id.btnRestart);
        textResult = findViewById(R.id.textResult);

        // Convert button click
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoursStr = inputHours.getText().toString().trim();
                String minutesStr = inputMinutes.getText().toString().trim();

                if (hoursStr.isEmpty()) hoursStr = "0";
                if (minutesStr.isEmpty()) minutesStr = "0";

                try {
                    double hours = Double.parseDouble(hoursStr);
                    double minutes = Double.parseDouble(minutesStr);

                    double totalSeconds = hours * 3600 + minutes * 60;

                    textResult.setText(String.format("%.0f seconds", totalSeconds));
                    btnShare.setVisibility(View.VISIBLE);
                    btnRestart.setVisibility(View.VISIBLE);

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Share button click
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = textResult.getText().toString();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Converted time: " + result + " (via Timely app)");
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        // Reset button click
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputHours.setText("");
                inputMinutes.setText("");
                textResult.setText("Result will appear here");
                btnShare.setVisibility(View.GONE);
                btnRestart.setVisibility(View.GONE);
            }
        });
    }
}
