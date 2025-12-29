package com.example.passwordmanager;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

/**
 * storageActivity2
 */
public class storageActivity2 extends AppCompatActivity {

    // UI
    private LinearLayout passwordsContainer;
    private TextView emptyMessage;
    private MaterialButton backButton;
    

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_storage2);
        

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // saves password
        sharedPreferences = getSharedPreferences("PasswordManager", MODE_PRIVATE);


        passwordsContainer = findViewById(R.id.passwordsContainer);
        emptyMessage = findViewById(R.id.emptyMessage);
        backButton = findViewById(R.id.backButton);

     // goes to the add password and username screen
        if (backButton != null) {
            backButton.setClickable(true);
            backButton.setFocusable(true);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }


        loadPasswords();
    }


    @Override
    protected void onResume() {
        super.onResume();

        loadPasswords();
    }

    private void loadPasswords() {

        passwordsContainer.removeAllViews();


        int passwordCount = sharedPreferences.getInt("password_count", 0);

        if (passwordCount == 0) {

            emptyMessage.setVisibility(View.VISIBLE);
            passwordsContainer.setVisibility(View.GONE);
        } else {

            emptyMessage.setVisibility(View.GONE);
            passwordsContainer.setVisibility(View.VISIBLE);


            for (int i = 1; i <= passwordCount; i++) {

                String website = sharedPreferences.getString("website_" + i, "");
                String username = sharedPreferences.getString("username_" + i, "");
                String password = sharedPreferences.getString("password_" + i, "");


                if (!website.isEmpty() && !username.isEmpty() && !password.isEmpty()) {

                    MaterialCardView card = createPasswordCard(website, username, password, i);
                    passwordsContainer.addView(card);
                }
            }
        }
    }


    private MaterialCardView createPasswordCard(String website, String username, String password, int index) {
        // Create a card (the container for this password)
        MaterialCardView card = new MaterialCardView(this);
        
        // Set card size and margins
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, // Full width
            LinearLayout.LayoutParams.WRAP_CONTENT  // Height based on content
        );
        cardParams.setMargins(0, 0, 0, 16); // 16dp margin at bottom
        card.setLayoutParams(cardParams);
        card.setCardElevation(2); // Shadow effect
        card.setCardBackgroundColor(getResources().getColor(R.color.input_background, null));

        // Create inner layout to hold text and button
        LinearLayout innerLayout = new LinearLayout(this);
        innerLayout.setOrientation(LinearLayout.VERTICAL); // Stack items vertically
        innerLayout.setPadding(24, 24, 24, 24); // Padding inside card

        // Create text view for website/app name (bold, large)
        TextView websiteText = new TextView(this);
        websiteText.setText(website);
        websiteText.setTextSize(18);
        websiteText.setTextColor(getResources().getColor(R.color.text_primary, null));
        websiteText.setTypeface(null, Typeface.BOLD);
        websiteText.setPadding(0, 0, 0, 8);

        // Create text view for username
        TextView usernameText = new TextView(this);
        usernameText.setText("Username: " + username);
        usernameText.setTextSize(14);
        usernameText.setTextColor(getResources().getColor(R.color.text_secondary, null));
        usernameText.setPadding(0, 0, 0, 4);

        // Create text view for password (hidden by default with dots)
        TextView passwordText = new TextView(this);
        passwordText.setText("Password: ••••••••");
        passwordText.setTextSize(14);
        passwordText.setTextColor(getResources().getColor(R.color.text_secondary, null));
        passwordText.setTag(password); // Store actual password in tag (hidden)
        passwordText.setPadding(0, 0, 0, 8);

        // Create button to show/hide password
        MaterialButton showPasswordButton = new MaterialButton(this);
        showPasswordButton.setText("Show Password");
        showPasswordButton.setTextSize(12);
        showPasswordButton.setCornerRadius(8);
        showPasswordButton.setBackgroundColor(getResources().getColor(R.color.primary_blue, null));
        showPasswordButton.setClickable(true);
        showPasswordButton.setFocusable(true);
        
        // Use a final array to store visibility state
        // This is needed because inner classes can't modify regular variables
        final boolean[] isVisible = {false}; // false = password is hidden
        
        // When button is clicked, toggle password visibility
        showPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible[0]) {
                    // Currently visible, so hide it
                    passwordText.setText("Password: ••••••••");
                    showPasswordButton.setText("Show Password");
                    isVisible[0] = false;
                } else {
                    // Currently hidden, so show it
                    String actualPassword = (String) passwordText.getTag(); // Get from tag
                    passwordText.setText("Password: " + actualPassword);
                    showPasswordButton.setText("Hide Password");
                    isVisible[0] = true;
                }
            }
        });

        // Add all views to the inner layout
        innerLayout.addView(websiteText);
        innerLayout.addView(usernameText);
        innerLayout.addView(passwordText);
        innerLayout.addView(showPasswordButton);

        // Add inner layout to the card
        card.addView(innerLayout);

        return card; // Return the completed card
    }
}