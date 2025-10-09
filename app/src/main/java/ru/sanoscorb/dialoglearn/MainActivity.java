package ru.sanoscorb.dialoglearn;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView timePickView;
    Button dateButton, timeButton, gameButton;
    Calendar dateTime = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener dateDialog = (view, year, month, dayOfMonth) -> {
        dateTime.set(Calendar.YEAR, year);
        dateTime.set(Calendar.MONTH, month);
        dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };

    TimePickerDialog.OnTimeSetListener timeDialog = (view, hourOfDay, minute) -> {
        dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateTime.set(Calendar.MINUTE, minute);
        setInitialDateTime();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        timePickView = findViewById(R.id.view_time_pick);
        dateButton = findViewById(R.id.button_date);
        dateButton.setOnClickListener(this);
        timeButton = findViewById(R.id.button_time);
        timeButton.setOnClickListener(this);
        gameButton = findViewById(R.id.button_game);
        gameButton.setOnClickListener(this);
        setInitialDateTime();
    }

    private void setInitialDateTime() {
        timePickView.setText(DateUtils.formatDateTime(
                this,
                dateTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME
        ));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_date) {
            new DatePickerDialog(MainActivity.this, dateDialog,
                    dateTime.get(Calendar.YEAR),
                    dateTime.get(Calendar.MONTH),
                    dateTime.get(Calendar.DAY_OF_MONTH))
                    .show();

            setInitialDateTime();
        }
        else if (v.getId() == R.id.button_time) {
            new TimePickerDialog(MainActivity.this, timeDialog,
                    dateTime.get(Calendar.HOUR_OF_DAY),
                    dateTime.get(Calendar.MINUTE),
                    true)
                    .show();

            setInitialDateTime();
        }
        else if (v.getId() == R.id.button_game) {
            new StartGameDialogFragment().show(getSupportFragmentManager(), "GAME_DIALOG");
        }
    }
}