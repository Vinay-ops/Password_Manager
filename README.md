# 🔐 Password Manager App

A simple and secure **Password Manager** application that allows users to safely store and manage their credentials using an **8-digit PIN or Biometric (Fingerprint) login system**.

The app focuses on ease of use while maintaining basic security principles for personal password storage.

---

## 📱 App Flow Overview

The application consists of **4 main screens**:

1. **Splash Screen** ([MainActivity.java](file:///app/src/main/java/com/example/passwordmanager/MainActivity.java)) - Initial branding and transition.
2. **Login Screen** ([LoginActivity.java](file:///app/src/main/java/com/example/passwordmanager/ui/LoginActivity.java)) - 8-digit PIN or Biometric (Fingerprint) authentication.
3. **Add Password Screen** ([StoreActivity.java](file:///app/src/main/java/com/example/passwordmanager/StoreActivity.java)) - Form to save new credentials (Website, Username, Password).
4. **Saved Passwords Screen** ([storageActivity2.java](file:///app/src/main/java/com/example/passwordmanager/storageActivity2.java)) - List view of all stored credentials with show/hide functionality.

---

## ✨ Features

### 🚀 Splash Screen
- Displays app logo and branding  
- Provides a smooth transition to the login screen  

### 🔢 8-digit PIN & Biometric Authentication
- User logs in using a secure 8-digit PIN
- Optional biometric (fingerprint) login for faster access
- Prevents unauthorized access to stored passwords  
- Authentication is validated before accessing any data  

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
- 8-digit PIN or Biometric login is used to restrict access to the app  
- Password fields are masked by default  
- No internet or cloud storage involved  

⚠️ This app is intended for **learning and personal use**.  
Advanced security features like AES encryption and cloud sync can be added in future versions.

---

## 🧰 Tech Stack

- **Platform:** Android  
- **Language:** Java  
- **UI:** XML layouts  
- **Storage:** Room Database  
- **Authentication:** 8-digit PIN & Biometric (Fingerprint)  

---

## 📂 Project Structure
 ```
PasswordManager/
├── app/src/main/java/com/example/passwordmanager/
│ ├── ui/
│ │ └── LoginActivity
│ ├── MainActivity
│ ├── StoreActivity
│ └── data/
│     ├── database/
│     ├── dao/
│     └── entity/
├── res/
├── README.md
└── AndroidManifest.xml
 ```

---

## 📸 App Screenshots

<p align="center">
  <img src="Screenshot/splash.jpeg" width="200" />
  <img src="Screenshot/login.png" width="200" />
  <img src="Screenshot/dashboard.jpeg" width="200" />
  <img src="Screenshot/stored_passwords.jpeg" width="200" />
</p>

<p align="center">
  <b>Splash Screen</b> &nbsp;&nbsp;&nbsp;
  <b>PIN Login</b> &nbsp;&nbsp;&nbsp;
  <b>Dashboard</b> &nbsp;&nbsp;&nbsp;
  <b>Add Password</b>
</p>


---

## 🛠️ Installation

1. Clone the repository:
   ```bash
    git clone https://github.com/Vinay-ops/Password_Manager
   ```

2. Open the project in Android Studio

3. Build and run the app on an emulator or physical device

4. Set your 8-digit PIN on first launch

---

## 🧪 Usage

Launch the app

Enter your 8-digit PIN or use Fingerprint to unlock

Add new passwords

View or manage saved credentials securely

---

## 🔮 Future Enhancements

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









