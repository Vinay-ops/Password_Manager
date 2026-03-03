package com.example.passwordmanager.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.passwordmanager.R;
import com.example.passwordmanager.StoreActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.example.passwordmanager.data.database.db;
import com.example.passwordmanager.data.dao.dao;
import com.example.passwordmanager.data.entity.Pin;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * LoginActivity - Simple PIN login screen
 * User enters an 8-digit PIN using the default system keyboard
 */
public class LoginActivity extends AppCompatActivity {

    // UI elements
    private TextInputEditText pinInput;
    private View pinDot1, pinDot2, pinDot3, pinDot4, pinDot5, pinDot6, pinDot7, pinDot8;
    private MaterialButton biometricButton;
    private TextView errorMessage;

    // Biometric authentication
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

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
        pinDot5 = findViewById(R.id.pinDot5);
        pinDot6 = findViewById(R.id.pinDot6);
        pinDot7 = findViewById(R.id.pinDot7);
        pinDot8 = findViewById(R.id.pinDot8);
        biometricButton = findViewById(R.id.biometricButton);
        errorMessage = findViewById(R.id.errorMessage);

        setupBiometrics();

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
            
            // If PIN exists, check if biometric is available and show button
            if (storedPin != null) {
                runOnUiThread(() -> {
                    checkBiometricAvailability();
                });
            }
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

                // If 8 digits entered, automatically check the PIN
                if (currentPin.length() == 8) {
                    checkPin();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed, but required by interface
            }
        });

        biometricButton.setOnClickListener(v -> {
            biometricPrompt.authenticate(promptInfo);
        });

        // Show keyboard when activity starts
        pinInput.requestFocus();
    }

    private void setupBiometrics() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(LoginActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                // Handle error
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                // Authentication succeeded, navigate to next screen
                navigateToMain();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for Password Manager")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use PIN")
                .build();
    }

    private void checkBiometricAvailability() {
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                biometricButton.setVisibility(View.VISIBLE);
                // Auto-prompt biometric if available
                biometricPrompt.authenticate(promptInfo);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
            case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
                biometricButton.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * Updates the visual dots to show how many digits have been entered
     * Filled dot = digit entered, Empty dot = no digit yet
     */
    private void updatePinDots() {
        pinDot1.setBackgroundResource(currentPin.length() >= 1 ? R.drawable.pin_dot_filled : R.drawable.pin_dot_empty);
        pinDot2.setBackgroundResource(currentPin.length() >= 2 ? R.drawable.pin_dot_filled : R.drawable.pin_dot_empty);
        pinDot3.setBackgroundResource(currentPin.length() >= 3 ? R.drawable.pin_dot_filled : R.drawable.pin_dot_empty);
        pinDot4.setBackgroundResource(currentPin.length() >= 4 ? R.drawable.pin_dot_filled : R.drawable.pin_dot_empty);
        pinDot5.setBackgroundResource(currentPin.length() >= 5 ? R.drawable.pin_dot_filled : R.drawable.pin_dot_empty);
        pinDot6.setBackgroundResource(currentPin.length() >= 6 ? R.drawable.pin_dot_filled : R.drawable.pin_dot_empty);
        pinDot7.setBackgroundResource(currentPin.length() >= 7 ? R.drawable.pin_dot_filled : R.drawable.pin_dot_empty);
        pinDot8.setBackgroundResource(currentPin.length() >= 8 ? R.drawable.pin_dot_filled : R.drawable.pin_dot_empty);
    }

    /**
     * Checks if the PIN is valid (8 digits)
     * If no PIN set, saves this PIN for the device and navigates
     */
    private void checkPin() {
        if (currentPin.length() != 8) return;

        if (storedPin == null) {
            ioExecutor.execute(() -> {
                pinDao.savePin(new Pin(currentPin));
                storedPin = currentPin;
                runOnUiThread(this::navigateToMain);
            });
        } else if (currentPin.equals(storedPin)) {
            navigateToMain();
        } else {
            errorMessage.setText("Incorrect PIN");
            errorMessage.setVisibility(View.VISIBLE);
            pinInput.setText("");
            currentPin = "";
            updatePinDots();
        }
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, StoreActivity.class);
        startActivity(intent);
        finish();
    }
}

