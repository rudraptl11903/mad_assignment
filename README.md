# AI Timetable Generator

A simple offline Android application developed in Kotlin using Android Studio for university Mobile Application Development assignments.

## Features
- **Dashboard (MainActivity)**: Displays student welcome card, metric badges, 4 quick actions, and today's class schedule.
- **Add Subject (AddSubjectActivity)**: Form with subject details, day selector Spinner, and TimePickerDialog for start/end times.
- **My Subjects (SubjectsActivity)**: Displays list of saved subjects with quick deletion controls.
- **My Timetable (TimetableActivity)**: Displays a weekly timetable grid and organized day-wise schedule.
- **Subject Progress (ProgressActivity)**: Visual indicators showing academic subject completion progress.
- **Settings (SettingsActivity)**: App appearance (Dark Mode switch), notifications, version info, and demo data controls.

## Technologies Used
- **Language**: Kotlin
- **Platform**: Android SDK 35 (Java 21)
- **UI Architecture**: Strict ConstraintLayout only (no LinearLayout, RelativeLayout, FrameLayout, TableLayout, CardView)
- **Storage**: SharedPreferences with local JSON serialization (100% offline, no external database or server)
