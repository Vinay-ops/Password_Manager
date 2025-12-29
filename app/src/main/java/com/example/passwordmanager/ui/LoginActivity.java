package com.example.passwordmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
     * If valid, navigates to StoreActivity
     */
    private void checkPin() {
        // Check if PIN has exactly 4 digits
        if (currentPin.length() == 4) {
            // PIN is valid - go to StoreActivity
            Intent intent = new Intent(LoginActivity.this, StoreActivity.class);
            startActivity(intent);
            finish(); // Close this activity so user can't go back
        }
    }
}