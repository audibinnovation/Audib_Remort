#  AudibRemote 🎛️

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


🤝 Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you’d like to change.

📄 License
This project is licensed under the MIT License — see the LICENSE file for details.
