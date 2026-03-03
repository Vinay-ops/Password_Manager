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

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.passwordmanager.data.PasswordViewModel
import com.example.passwordmanager.data.PasswordEntity
import com.example.passwordmanager.ui.components.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoredPasswordsScreen(
    onBackClick: () -> Unit = {},
    passwordViewModel: PasswordViewModel = viewModel()
) {
    val categories = listOf("All", "Social", "Banking", "Work")
    var selectedCategory by remember { mutableStateOf("All") }
    var showAddDialog by remember { mutableStateOf(false) }
    
    val passwordsFlow = remember(selectedCategory) {
        if (selectedCategory == "All") passwordViewModel.allPasswords 
        else passwordViewModel.getPasswordsByCategory(selectedCategory)
    }
    val passwords by passwordsFlow.collectAsState(initial = emptyList())
    
    // Form states
    var newServiceName by remember { mutableStateOf("") }
    var newUserName by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var newCategory by remember { mutableStateOf("Social") }

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
                        border = BorderStroke(1.dp, Color(0xFF6366F1).copy(alpha = 0.3f)),
                        onClick = onBackClick
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Vaultiq",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF6366F1),
                    onClick = { showAddDialog = true }
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
            }
        }

        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Add New Password", color = Color.White) },
                containerColor = Color(0xFF1B1A35),
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = newServiceName,
                            onValueChange = { newServiceName = it },
                            label = { Text("Service Name") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            )
                        )
                        OutlinedTextField(
                            value = newUserName,
                            onValueChange = { newUserName = it },
                            label = { Text("Username / Email") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            )
                        )
                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            label = { Text("Password") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            )
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newServiceName.isNotBlank() && newPassword.isNotBlank()) {
                                passwordViewModel.addPassword(
                                    PasswordEntity(
                                        serviceName = newServiceName,
                                        userName = newUserName,
                                        passwordEncrypted = newPassword,
                                        category = newCategory
                                    )
                                )
                                showAddDialog = false
                                newServiceName = ""
                                newUserName = ""
                                newPassword = ""
                            }
                        }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) {
                        Text("Cancel", color = Color.White.copy(alpha = 0.6f))
                    }
                }
            )
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
                BottomNavItem("GENERATOR", Icons.Default.VpnKey, false)
                BottomNavItem("AUDIT", Icons.Default.Security, false)
                BottomNavItem("MENU", Icons.Default.Menu, false)
            }
        }
    }
}

@Composable
fun StoredPasswordItem(data: PasswordEntity) {
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
                    Text(text = data.serviceName, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color(0xFF6366F1).copy(alpha = 0.1f),
                    ) {
                        Text(
                            text = data.category.uppercase(),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            color = Color(0xFF6366F1),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Text(text = data.userName, color = Color.White.copy(alpha = 0.4f), fontSize = 12.sp)
                Text(text = "••••••••", color = Color.White.copy(alpha = 0.4f), fontSize = 12.sp)
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

@Preview(showBackground = true)
@Composable
fun StoredPasswordsScreenPreview() {
    VaultTheme {
        // Dummy data for preview
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            Text("Preview requires ViewModel mock", color = Color.White)
        }
    }
}
