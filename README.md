# AudibControl 🎛️

AudibControl is a modern Android remote control app built with **Material 3**, **Kotlin coroutines**, and **Jetpack best practices**.  
It provides a polished UI for managing Bluetooth audio devices, inputs, FM controls, and system features — all in one place.

---

## ✨ Features

- **Complete Remote Layout**
  - Bluetooth controls (Enable, Scan, Connect, Disconnect)
  - Power, Volume cluster (Vol‑, Mute, Vol+)
  - Inputs (IN0–IN4, Prev/Next)
  - Audio features (Menu, Loudness, Surround, 3D, Tone Bypass)
  - FM controls (Up, Down, Mode, RDS, Store, Mono)
  - Numeric keypad (0–9)
  - System controls (Time, Alarm, Timer, Display, Spectrum, Full Speed, Bright)

- **Modern UI**
  - Built with `ConstraintLayout` for screen‑fit design
  - Consistent global styles (`RemoteButtonStyle`, `RemoteCardStyle`)
  - Dark theme using `Theme.Material3.Dark.NoActionBar`

- **Bluetooth Stability**
  - Connection logic migrated to Kotlin coroutines
  - Auto‑reconnect with state/permission checks
  - Robust socket lifecycle management

---

## 📲 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/AudibRemote.git
2. 	Open in Android Studio (latest version recommended).
3. 	Build and run on a device (tested on Nothing Phone 3, API 34).

🚀 Usage
• 	Launch the app and connect via Bluetooth.
• 	Use the remote UI to control volume, inputs, audio features, FM, and system functions.
• 	Status bar shows connection state and feedback.

📝 Release Notes
v1.1 (December 2025)
• 	Migrated Bluetooth connection logic to coroutines for safer lifecycle management.
• 	Improved permission handling with clear feedback on grant/deny.
• 	Auto‑reconnect now checks Bluetooth state and permissions before attempting.
• 	Fixed race conditions from multiple connect attempts.
• 	now auto‑disconnects on failure to avoid broken sockets.
• 	Cleaned up socket lifecycle with consistent close/reset.
• 	Better status reporting and user feedback on errors.
• 	Removed unused exception parameter warning.
• 	Complete remote layout with all buttons included.
• 	Theme cleanup and Material 3 adoption.

📢 Release Notes
v1.3
• 	Fixed crash when enabling Bluetooth without Nearby Devices permission
• 	Added dark blue glow effect for buttons
• 	Status text separated from system notification bar
• 	Expanded color palette with semantic states (Connected, Disconnected, Scanning)

 v1.4
• 	Introduced Material3 Dark Theme
• 	Added global styles for buttons and cards
• 	Implemented FlexboxLayout for responsive button arrangement
• 	Auto‑reconnect to last paired Bluetooth device
• 	Improved amplifier command mapping and error handling

🛠 Tech Stack
• 	Language: Kotlin
• 	UI: Material 3, ConstraintLayout, Jetpack Compose (planned)
• 	Bluetooth: Classic SPP bridge (ESP32 tested)
• 	Architecture: ViewBinding, Coroutines, idiomatic Kotlin

🤝 Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you’d like to change.

📄 License
This project is licensed under the MIT License — see the LICENSE file for details.
