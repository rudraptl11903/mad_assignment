# AI Timetable Generator

An Android application developed using **Kotlin** and **XML** to help students organize subjects and generate a simple timetable using rule-based logic.

> **Note:** This project uses rule-based timetable generation for academic/assignment purposes. It does not use an external machine-learning model.

## Features

- Add student name and subjects
- Rule-based weekly timetable generation (Monday to Friday)
- Dynamic timetable rotation / regeneration
- About project information screen
- Clean, scrollable Material UI design with card elements
- Offline functionality
- No Firebase required
- No external API required

## Technology

| Technology | Purpose |
|---|---|
| Kotlin | Application programming |
| XML | User interface |
| Android Studio | Development IDE |
| ConstraintLayout | Responsive UI layout |
| Material Components | Modern styling and theming |
| Gradle | Build management |

## Project Structure

```text
AI-Timetable-Generator/
│
├── app/
│   ├── src/main/
│   │   ├── java/com/example/mad_assignment/
│   │   │   ├── MainActivity.kt
│   │   │   ├── TimetableActivity.kt
│   │   │   └── AboutActivity.kt
│   │   │
│   │   ├── res/
│   │   │   ├── drawable/
│   │   │   ├── layout/
│   │   │   ├── mipmap-*/
│   │   │   └── values/
│   │   │
│   │   └── AndroidManifest.xml
│   │
│   └── build.gradle.kts
│
├── gradle/
├── build.gradle.kts
├── gradle.properties
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
└── .gitignore
```

## Main Activities

### MainActivity
Main/home screen where students enter their name and subjects, with options to generate a timetable or view project info.

```text
app/src/main/java/com/example/mad_assignment/MainActivity.kt
```

### TimetableActivity
Displays the weekly timetable from Monday to Friday, distributing entered subjects across weekdays, with options to regenerate (rotate) the timetable or return home.

```text
app/src/main/java/com/example/mad_assignment/TimetableActivity.kt
```

### AboutActivity
Displays information about the application, technologies used, and academic purpose.

```text
app/src/main/java/com/example/mad_assignment/AboutActivity.kt
```

## Application Flow

```text
       Start Application
              ↓
         MainActivity
      (Enter Subjects)
        ↙           ↘
TimetableActivity    AboutActivity
(View Timetable)     (Project Info)
```

## Rule-Based AI Logic

For this academic project, the **AI Timetable Generator** uses predefined rules rather than a heavy ML model:

- Collects entered student subjects and filters non-empty inputs
- Evenly distributes subjects across Monday to Friday
- Supports rotation/regeneration of slots on user request
- Automatically assigns "Free / Self Study" when slots are unassigned

## Installation

### Requirements

- Android Studio (Ladybug or newer recommended)
- Android SDK (API 36 / 37 compatible)
- JDK 11 or higher
- Android device or emulator

### Run the Project

1. Clone the repository:

```bash
git clone https://github.com/rudraptl11903/mad_assignment.git
```

2. Open the project in Android Studio.
3. Allow Gradle to sync.
4. Connect an Android device or start an emulator.
5. Click **Run ▶**.

## Academic Information

**Project:** AI Timetable Generator  
**Platform:** Android  
**Language:** Kotlin  
**UI:** XML  
**IDE:** Android Studio  
**Mode:** Offline  

## Author

**Rudra Patel**

## License

This project is created for educational and academic purposes.
