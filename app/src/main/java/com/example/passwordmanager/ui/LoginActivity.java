package com.example.passwordmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passwordmanager.R;
import com.example.passwordmanager.StoreActivity;
import com.example.passwordmanager.data.database.db;
import com.example.passwordmanager.data.entity.Pin;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText pinEditText;
    private String currentPin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pinEditText = findViewById(R.id.pinInput);

        pinEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentPin = s.toString();

                if (currentPin.length() == 4) {
                    checkPin(currentPin);
                }
            }

            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void checkPin(String enteredPin) {

        new Thread(() -> {

            db database = db.getDatabase(this);
            String savedPin = database.pinDao().getPin();

            runOnUiThread(() -> {

                // FIRST TIME LOGIN → SAVE PIN
                if (savedPin == null) {
                    database.pinDao().savePin(new Pin(enteredPin));
                    goToStore();
                    return;
                }

                // CORRECT PIN
                if (savedPin.equals(enteredPin)) {
                    goToStore();
                }
                // WRONG PIN
                else {
                    Toast.makeText(this, "Wrong PIN", Toast.LENGTH_SHORT).show();
                    pinEditText.setText("");
                }
            });

        }).start();
    }

    private void goToStore() {
        startActivity(new Intent(this, StoreActivity.class));
        finish();
    }
}
