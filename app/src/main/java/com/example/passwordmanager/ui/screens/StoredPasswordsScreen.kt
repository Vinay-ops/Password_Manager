package com.example.passwordmanager.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordmanager.ui.theme.VaultTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoredPasswordsScreen() {
    val categories = listOf("All", "Social", "Banking", "Work")
    var selectedCategory by remember { mutableStateOf("All") }
    
    val passwords = listOf(
        StoredPasswordData("Google", "alex.design@gmail....", "••••••", "WORK", Color(0xFF6366F1)),
        StoredPasswordData("Netflix", "family_account@ne...", "•••••", "SOCIAL", Color(0xFFF97316)),
        StoredPasswordData("Spotify P...", "music_lover_99", "••••", "SOCIAL", Color(0xFFF97316)),
        StoredPasswordData("Chase ...", "ad_official_2024", "••••••••", "BANKING", Color(0xFF10B981)),
        StoredPasswordData("Adobe Cl...", "alex_designs_01@a...", "••••", "WORK", Color(0xFF6366F1))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0A))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF1B1A35).copy(alpha = 0.5f),
                        border = BorderStroke(1.dp, Color(0xFF6366F1).copy(alpha = 0.3f))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🛡️", fontSize = 20.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Vault",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF1B1A35).copy(alpha = 0.5f),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "ALL PASSWORDS",
                color = Color.White.copy(alpha = 0.4f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            Surface(
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF1B1A35).copy(alpha = 0.5f),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.White.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Search 42 items...", color = Color.White.copy(alpha = 0.3f), fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Categories
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = category == selectedCategory
                    Surface(
                        modifier = Modifier.height(44.dp).weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) Color(0xFF6366F1) else Color(0xFF1B1A35).copy(alpha = 0.3f),
                        onClick = { selectedCategory = category }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = category,
                                color = if (isSelected) Color.White else Color.White.copy(alpha = 0.4f),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Stored Password List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(passwords) { item ->
                    StoredPasswordItem(item)
                }
                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
        }

        // Bottom Bar
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(90.dp),
            color = Color(0xFF0A0A0A),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem("VAULT", Icons.Default.Lock, true)
                BottomNavItem("GENERATOR", Icons.Default.VpnKey, false)
                BottomNavItem("AUDIT", Icons.Default.Security, false)
                BottomNavItem("MENU", Icons.Default.Menu, false)
            }
        }
    }
}

@Composable
fun StoredPasswordItem(data: StoredPasswordData) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFF1B1A35).copy(alpha = 0.4f),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF1B1A35).copy(alpha = 0.5f),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = Color.White)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = data.name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = data.tagColor.copy(alpha = 0.1f),
                    ) {
                        Text(
                            text = data.tag,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            color = data.tagColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Text(text = data.username, color = Color.White.copy(alpha = 0.4f), fontSize = 12.sp)
                Text(text = data.password, color = Color.White.copy(alpha = 0.4f), fontSize = 12.sp)
            }
            Row {
                IconButton(onClick = { /* TODO */ }) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, tint = Color.White.copy(alpha = 0.5f), modifier = Modifier.size(20.dp))
                }
                IconButton(onClick = { /* TODO */ }) {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White.copy(alpha = 0.5f), modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

data class StoredPasswordData(val name: String, val username: String, val password: String, val tag: String, val tagColor: Color)

@Preview(showBackground = true)
@Composable
fun StoredPasswordsScreenPreview() {
    VaultTheme {
        StoredPasswordsScreen()
    }
}
