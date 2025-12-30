# 🔐 Password Manager App

A simple and secure **Password Manager** application that allows users to safely store and manage their credentials using a **PIN-based login system**.

The app focuses on ease of use while maintaining basic security principles for personal password storage.

---

## 📱 App Flow Overview

1. Splash Screen  
2. PIN Login Screen  
3. Dashboard (Saved Passwords)  
4. Add New Password Screen  

---

## ✨ Features

### 🚀 Splash Screen
- Displays app logo and branding  
- Provides a smooth transition to the login screen  

### 🔢 PIN Login Authentication
- User logs in using a secure PIN  
- Prevents unauthorized access to stored passwords  
- PIN is validated before accessing any data  

### ➕ Add Password Screen
- Add credentials with:
  - App / Website name  
  - Username or Email  
  - Password  
- Input fields are masked for privacy  
- Data is saved securely in local storage  

### 📂 View Saved Passwords
- List of all stored credentials  
- Options to:
  - View password  
  - Copy password  
  - Delete saved entry  
- Clean and minimal UI for easy access  

---

## 🛡️ Security Considerations

- All data is stored locally on the device  
- PIN is used to restrict access to the app  
- Password fields are masked by default  
- No internet or cloud storage involved  

⚠️ This app is intended for **learning and personal use**.  
Advanced security features like biometric login, encryption key management, and cloud sync can be added in future versions.

---

## 🧰 Tech Stack

- **Platform:** Android  
- **Language:** Java / Kotlin  
- **UI:** XML layouts  
- **Storage:** SharedPreferences / Local Database  
- **Authentication:** PIN-based login  

---

## 📂 Project Structure
 ```
PasswordManager/
├── activities/
│ ├── SplashActivity
│ ├── PinLoginActivity
│ ├── AddPasswordActivity
│ └── PasswordListActivity
├── models/
├── utils/
├── res/
├── README.md
└── AndroidManifest.xml
 ```

---

# 📸 App Screenshots

### 🚀 Splash Screen
![Splash Screen](Screenshot/splash.jpg)

### 🔢 PIN Login Screen
![PIN Login Screen](Screenshot/login.jpg)

### 📂 Saved Passwords Screen
![Saved Passwords Screen](Screenshot/dashboard.jpg)

### ➕ Add Password Screen
![Add Password Screen](Screenshot/stored_passwords.jpg)

---

## 🛠️ Installation

1. Clone the repository:
   ```bash
    git clone https://github.com/Vinay-ops/Password_Manager

2. Open the project in Android Studio

3. Build and run the app on an emulator or physical device

4. Set your PIN on first launch

---

## 🧪 Usage

Launch the app

Enter your PIN to unlock

Add new passwords

View or manage saved credentials securely

---

## 🔮 Future Enhancements

🔐 Fingerprint / Face ID login

🔑 Strong encryption (AES)

🔄 Edit saved passwords

☁️ Encrypted cloud backup

⏱️ Auto-lock after inactivity

---


## 🧪 Usage

Launch the app

Enter your PIN to unlock

Add new passwords

View or manage saved credentials securely

---


## 🔮 Future Enhancements

🔐 Fingerprint / Face ID login

🔑 Strong encryption (AES)

🔄 Edit saved passwords

☁️ Encrypted cloud backup

⏱️ Auto-lock after inactivity
  
---
## 👤 Author

Vinay Bhogal
Student Developer | Android App Development






