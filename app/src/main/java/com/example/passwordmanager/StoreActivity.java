package com.example.passwordmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * StoreActivity - Save Password Screen
 * User can enter website, username, and password to save
 * Uses SharedPreferences for simple storage (no database needed)
 */
public class StoreActivity extends AppCompatActivity {

    // UI elements - input fields
    private TextInputEditText websiteEditText;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    
    // UI elements - layouts (for showing errors)
    private TextInputLayout websiteInputLayout;
    private TextInputLayout usernameInputLayout;
    private TextInputLayout passwordInputLayout;
    
    // UI elements - buttons and messages
    private MaterialButton saveButton;
    private MaterialButton viewPasswordsButton;
    private TextView successMessage;
    
    // Storage - SharedPreferences is like a simple key-value storage
    private SharedPreferences sharedPreferences;
    
    // Keep track of how many passwords we've saved
    private int passwordCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_store);
        
        // Handle system bars (notch, status bar, etc.)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize SharedPreferences - this is where we store passwords
        // "PasswordManager" is the name of our storage file
        sharedPreferences = getSharedPreferences("PasswordManager", MODE_PRIVATE);
        
        // Get the current password count (how many passwords saved so far)
        passwordCount = sharedPreferences.getInt("password_count", 0);

        // Find all UI elements by their IDs
        websiteEditText = findViewById(R.id.websiteEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        websiteInputLayout = findViewById(R.id.websiteInputLayout);
        usernameInputLayout = findViewById(R.id.usernameInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        saveButton = findViewById(R.id.saveButton);
        viewPasswordsButton = findViewById(R.id.viewPasswordsButton);
        successMessage = findViewById(R.id.successMessage);

        // When user clicks Save button, save the password
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePassword();
            }
        });

        // When user clicks View Passwords button, go to storage activity
        viewPasswordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivity.this, storageActivity2.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Saves the password to SharedPreferences
     * First checks if all fields are filled, then saves
     */
    private void savePassword() {
        // Get text from input fields and remove extra spaces
        String website = websiteEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        // Clear any previous error messages
        websiteInputLayout.setError(null);
        usernameInputLayout.setError(null);
        passwordInputLayout.setError(null);
        successMessage.setVisibility(View.GONE);

        // Check if website field is empty
        if (website.isEmpty()) {
            websiteInputLayout.setError("Please enter website or app name");
            websiteEditText.requestFocus(); // Put cursor in this field
            return; // Stop here, don't save
        }

        // Check if username field is empty
        if (username.isEmpty()) {
            usernameInputLayout.setError("Please enter username or email");
            usernameEditText.requestFocus();
            return;
        }

        // Check if password field is empty
        if (password.isEmpty()) {
            passwordInputLayout.setError("Please enter password");
            passwordEditText.requestFocus();
            return;
        }

        // All fields are filled, so save the password
        passwordCount++; // Increase count by 1
        
        // Get editor to write to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        
        // Save each field with a unique key
        // Example: "website_1", "website_2", etc.
        editor.putString("website_" + passwordCount, website);
        editor.putString("username_" + passwordCount, username);
        editor.putString("password_" + passwordCount, password);
        editor.putInt("password_count", passwordCount); // Save the count too
        editor.apply(); // Actually save the data

        // Show success message
        successMessage.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Password saved successfully!", Toast.LENGTH_SHORT).show();

        // Clear all input fields so user can enter another password
        websiteEditText.setText("");
        usernameEditText.setText("");
        passwordEditText.setText("");

        // Hide success message after 3 seconds
        successMessage.postDelayed(new Runnable() {
            @Override
            public void run() {
                successMessage.setVisibility(View.GONE);
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }
}