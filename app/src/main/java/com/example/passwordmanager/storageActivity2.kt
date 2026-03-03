package com.example.passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.passwordmanager.ui.screens.StoredPasswordsScreen
import com.example.passwordmanager.ui.theme.VaultTheme

class storageActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VaultTheme {
                StoredPasswordsScreen(
                    onBackClick = {
                        finish()
                    }
                )
            }
        }
    }
}
