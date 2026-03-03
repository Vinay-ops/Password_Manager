package com.example.passwordmanager.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.passwordmanager.ui.components.BottomNavItem
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.passwordmanager.data.PasswordViewModel
import com.example.passwordmanager.data.PasswordEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToStored: () -> Unit = {},
    passwordViewModel: PasswordViewModel = viewModel()
) {
    val categories = listOf("Social", "Banking", "Work", "Shopping")
    var selectedCategory by remember { mutableStateOf("Social") }
    
    val passwordsFlow = remember(selectedCategory) {
        passwordViewModel.getPasswordsByCategory(selectedCategory)
    }
    val passwords by passwordsFlow.collectAsState(initial = emptyList())

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
                    Text(
                        text = "Vaultiq",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    color = Color(0xFF1B1A35).copy(alpha = 0.5f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = Color(0xFF6366F1))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

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
                    Text("Search Vaultiq...", color = Color.White.copy(alpha = 0.3f), fontSize = 16.sp)
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

            // Password List Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Passwords",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = onNavigateToStored) {
                    Text("See All", color = Color(0xFF6366F1))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(passwords) { item ->
                    PasswordItem(item)
                }
                
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    // Security Health Card
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        color = Color(0xFF6366F1).copy(alpha = 0.9f)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Text(
                                text = "Security Health",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "85% of your passwords are strong. Great job!",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 14.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }

        // FAB
        LargeFloatingActionButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 120.dp, end = 24.dp),
            containerColor = Color(0xFF6366F1),
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(32.dp))
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
                BottomNavItem("Vaultiq", Icons.Default.Lock, true)
                BottomNavItem("Generator", Icons.Default.VpnKey, false)
                BottomNavItem("Security", Icons.Default.Security, false)
                BottomNavItem("Settings", Icons.Default.Settings, false)
            }
        }
    }
}

@Composable
fun PasswordItem(data: PasswordEntity) {
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
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF6366F1).copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(data.serviceName.take(1).uppercase(), color = Color(0xFF6366F1), fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = data.serviceName, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "••••••••", color = Color.White.copy(alpha = 0.4f), fontSize = 12.sp)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.White.copy(alpha = 0.3f))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    VaultTheme {
        // Dummy data for preview
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            Text("Preview requires ViewModel mock", color = Color.White)
        }
    }
}
