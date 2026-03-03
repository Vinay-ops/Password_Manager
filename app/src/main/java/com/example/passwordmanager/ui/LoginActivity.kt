package com.example.passwordmanager.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.passwordmanager.StoreActivity
import com.example.passwordmanager.ui.screens.LoginScreen
import com.example.passwordmanager.ui.theme.VaultTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VaultTheme {
                LoginScreen(
                    onSignInClick = {
                        startActivity(Intent(this, StoreActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}
