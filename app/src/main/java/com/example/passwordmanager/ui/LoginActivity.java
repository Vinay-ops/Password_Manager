package com.example.passwordmanager.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.passwordmanager.R;
import com.example.passwordmanager.StoreActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.example.passwordmanager.data.database.db;
import com.example.passwordmanager.data.dao.dao;
import com.example.passwordmanager.data.entity.Pin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * LoginActivity - Simple PIN login screen
 * User enters a 4-digit PIN using the default system keyboard
 */
public class LoginActivity extends AppCompatActivity {

    // UI elements
    private TextInputEditText pinInput;
    private View pinDot1, pinDot2, pinDot3, pinDot4;
    private TextView errorMessage;

    // Store the current PIN as user types
    private String currentPin = "";
    private String storedPin = null;
    private dao pinDao;
    private final ExecutorService ioExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Handle system bars (notch, status bar, etc.)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find all UI elements by their IDs
        pinInput = findViewById(R.id.pinInput);
        pinDot1 = findViewById(R.id.pinDot1);
        pinDot2 = findViewById(R.id.pinDot2);
        pinDot3 = findViewById(R.id.pinDot3);
        pinDot4 = findViewById(R.id.pinDot4);
        errorMessage = findViewById(R.id.errorMessage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pinInput.setAutofillHints((String[]) null);
        }
        pinInput.setTextIsSelectable(false);
        pinInput.setPrivateImeOptions("disableDirectSuggestions=true");
        pinInput.setSaveEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pinInput.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);
        }

        pinDao = db.getDatabase(getApplicationContext()).pinDao();
        ioExecutor.execute(() -> {
            String pin = pinDao.getPin();
            storedPin = pin;
        });

        // Listen for text changes in PIN input field
        pinInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed, but required by interface
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update current PIN when user types
                currentPin = s.toString();

                // Update the visual dots to show how many digits entered
                updatePinDots();

                // If 4 digits entered, automatically check the PIN
                if (currentPin.length() == 4) {
                    checkPin();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed, but required by interface
            }
        });

        // Show keyboard when activity starts
        pinInput.requestFocus();
    }

    /**
     * Updates the visual dots to show how many digits have been entered
     * Filled dot = digit entered, Empty dot = no digit yet
     */
    private void updatePinDots() {
        // Update dot 1
        if (currentPin.length() >= 1) {
            pinDot1.setBackgroundResource(R.drawable.pin_dot_filled);
        } else {
            pinDot1.setBackgroundResource(R.drawable.pin_dot_empty);
        }

        // Update dot 2
        if (currentPin.length() >= 2) {
            pinDot2.setBackgroundResource(R.drawable.pin_dot_filled);
        } else {
            pinDot2.setBackgroundResource(R.drawable.pin_dot_empty);
        }

        // Update dot 3
        if (currentPin.length() >= 3) {
            pinDot3.setBackgroundResource(R.drawable.pin_dot_filled);
        } else {
            pinDot3.setBackgroundResource(R.drawable.pin_dot_empty);
        }

        // Update dot 4
        if (currentPin.length() >= 4) {
            pinDot4.setBackgroundResource(R.drawable.pin_dot_filled);
        } else {
            pinDot4.setBackgroundResource(R.drawable.pin_dot_empty);
        }
    }

    /**
     * Checks if the PIN is valid (4 digits)
     * If no PIN set, saves this PIN for the device and navigates
     */
    private void checkPin() {
        if (currentPin.length() != 4) return;

        if (storedPin == null) {
            ioExecutor.execute(() -> {
                pinDao.savePin(new Pin(currentPin));
                storedPin = currentPin;
                runOnUiThread(() -> {
                    Intent intent = new Intent(LoginActivity.this, StoreActivity.class);
                    startActivity(intent);
                    finish();
                });
            });
        } else if (currentPin.equals(storedPin)) {
            Intent intent = new Intent(LoginActivity.this, StoreActivity.class);
            startActivity(intent);
            finish();
        } else {
            errorMessage.setText("Incorrect PIN");
            errorMessage.setVisibility(View.VISIBLE);
            pinInput.setText("");
            currentPin = "";
            updatePinDots();
        }
    }
}
