📱 AudibRemote – Android Bluetooth Remote
AudibRemote is an Android application built in Android Studio that lets you control your amplifier or audio system via Bluetooth. It provides a modern Material-themed UI, responsive Flexbox layout, and robust Bluetooth connectivity with auto-reconnect support.

✨ Features
• 	🔌 Bluetooth Controls
• 	Enable/disable Bluetooth
• 	Scan and select paired devices
• 	Connect/disconnect with auto-reconnect
• 	🎛️ Amplifier Commands
• 	Power toggle
• 	Volume up/down, mute
• 	Input selection (, next/previous input)
• 	Audio features: Menu, Loudness, Surround, 3D, Tone Bypass
• 	📻 FM Controls
• 	FM tuning up/down
• 	Mode, RDS, Store, Mono
• 	🔢 Numeric Keypad
• 	Digits  for quick input
• 	🕒 System Controls
• 	Time, Alarm, Timer
• 	Display, Spectrum, Brightness, Full Speed

🛠️ Tech Stack
• 	Language: Kotlin
• 	UI: XML with FlexboxLayout + Material Components
• 	Architecture: ViewBinding-ready, modular command mapping
• 	Bluetooth: Classic SPP ()
• 	Persistence: SharedPreferences (KTX) for last device auto-reconnect
• 	Theme: Material Dark with custom colors (black background, teal accents)

📂 Project Structure
• 	 → Bluetooth lifecycle, command mapping, connection management
• 	 → Flexbox-based remote control UI
• 	 → Permissions and activity declaration
• 	 → Labels for all controls
• 	 → Color palette
• 	 → Material theme setup

🚀 Getting Started
1. 	Clone the repository:

2. 	Open in Android Studio.
3. 	Build and run on your device (tested on Nothing Phone 3).
4. 	Pair your amplifier/audio system via Bluetooth.
5. 	Launch the app and start controlling!

📸 Screenshots
(Add screenshots here once you capture them from your device)

⚡ Future Improvements
• 	Migrate to ViewBinding + Coroutines for cleaner lifecycle-aware code.
• 	Add MaterialCardView grouping for better UI readability.
• 	Integrate icons for common actions (Power, Volume, Bluetooth).
• 	Improve accessibility with content descriptions.

📄 License
This project is licensed under the MIT License – feel free to use and modify.
