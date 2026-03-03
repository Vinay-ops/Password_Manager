package com.example.passwordmanager

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.passwordmanager.ui.screens.DashboardScreen
import com.example.passwordmanager.ui.theme.VaultTheme

class StoreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VaultTheme {
                DashboardScreen(
                    onNavigateToStored = {
                        startActivity(Intent(this, storageActivity2::class.java))
                    }
                )
            }
        }
    }
}
