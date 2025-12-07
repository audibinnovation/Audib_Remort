# 📱 AudibRemote – Android Bluetooth Remote

AudibRemote is an Android application built in **Android Studio** that lets you control your amplifier or audio system via Bluetooth. It provides a modern Material-themed UI, responsive Flexbox layout, and robust Bluetooth connectivity with auto-reconnect support.

---

## ✨ Features
- 🔌 **Bluetooth Controls**
  - Enable/disable Bluetooth
  - Scan and select paired devices
  - Connect/disconnect with auto-reconnect

- 🎛️ **Amplifier Commands**
  - Power toggle
  - Volume up/down, mute
  - Input selection (`IN0–IN4`, next/previous input)
  - Audio features: Menu, Loudness, Surround, 3D, Tone Bypass

- 📻 **FM Controls**
  - FM tuning up/down
  - Mode, RDS, Store, Mono

- 🔢 **Numeric Keypad**
  - Digits `0–9` for quick input

- 🕒 **System Controls**
  - Time, Alarm, Timer
  - Display, Spectrum, Brightness, Full Speed

---

## 🛠️ Tech Stack
- **Language:** Kotlin
- **UI:** XML with FlexboxLayout + Material Components
- **Architecture:** ViewBinding-ready, modular command mapping
- **Bluetooth:** Classic SPP (`00001101-0000-1000-8000-00805F9B34FB`)
- **Persistence:** SharedPreferences (KTX) for last device auto-reconnect
- **Theme:** Material Dark with custom colors (black background, teal accents)

---

## 📂 Project Structure
- `MainActivity.kt` → Bluetooth lifecycle, command mapping, connection management
- `activity_main.xml` → Flexbox-based remote control UI
- `AndroidManifest.xml` → Permissions and activity declaration
- `res/values/strings.xml` → Labels for all controls
- `res/values/colors.xml` → Color palette
- `res/values/themes.xml` → Material theme setup

---

## 🚀 Getting Started
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/AudibRemote.git
