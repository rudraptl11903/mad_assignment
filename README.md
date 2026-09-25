# AI Timetable Generator

## Project Overview
AI Timetable Generator is a simple Android application developed as a university Mobile Application Development assignment. It helps students organize their weekly class timetable by entering subject and schedule information.

The project is intentionally simple and beginner-friendly so it can be easily understood and explained during a viva.

## Objective
- Learn basic Android application development.
- Design Android interfaces using XML.
- Use Kotlin for application logic.
- Handle user input and button events.
- Navigate between multiple screens.
- Display timetable information.

## Features
### Home Screen
- Add Subject
- My Timetable
- Progress
- Generate
- Today's Schedule
- Subjects
- Settings

### Add Subject
The user can enter:
- Subject name
- Teacher name
- Day
- Start time
- End time
- Room number

### My Timetable
Displays timetable information in a simple weekly format.

### Progress
Provides a simple area for displaying study progress.

### Settings
Contains basic settings such as dark mode, class reminder, and application information.

## Technology Used

| Technology | Details |
|---|---|
| Language | Kotlin |
| IDE | Android Studio |
| UI | XML |
| Layout | ConstraintLayout |
| Minimum SDK | API 24 |
| Target SDK | API 35 |
| Build System | Gradle |
| Database | Not required |
| Firebase | Not used |
| Internet/API | Not required |

## Important Constraint
All application XML layouts use **ConstraintLayout**.

The project does not use:
- LinearLayout
- RelativeLayout
- FrameLayout
- Jetpack Compose

## Project Structure

```text
AI_Timetable_Generator/
├── app/
│   └── src/main/
│       ├── java/com/example/aitimetable/
│       │   ├── MainActivity.kt
│       │   ├── AddSubjectActivity.kt
│       │   ├── SubjectsActivity.kt
│       │   ├── TimetableActivity.kt
│       │   ├── ProgressActivity.kt
│       │   └── SettingsActivity.kt
│       │
│       ├── res/
│       │   ├── layout/
│       │   │   ├── activity_main.xml
│       │   │   ├── activity_add_subject.xml
│       │   │   ├── activity_subjects.xml
│       │   │   ├── activity_timetable.xml
│       │   │   ├── activity_progress.xml
│       │   │   └── activity_settings.xml
│       │   ├── drawable/
│       │   └── values/
│       │       ├── colors.xml
│       │       └── themes.xml
│       │
│       └── AndroidManifest.xml
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Application Flow

```text
Start Application
       |
       v
  Home Screen
       |
       +----> Add Subject
       |          |
       |          v
       |     Enter Details
       |
       +----> My Timetable
       |          |
       |          v
       |     View Timetable
       |
       +----> Progress
       |
       +----> Generate
       |
       +----> Subjects
       |
       +----> Settings
```

## Add Subject Working

Example:

```text
Subject Name : Android Development
Teacher Name : Prof. Patel
Day          : Monday
Start Time   : 09:00 AM
End Time     : 10:00 AM
Room Number  : A-101
```

After pressing **ADD SUBJECT**, the application validates the subject name and displays a confirmation message.

## Timetable Generation

The project uses simple timetable logic suitable for a beginner-level assignment. The application displays timetable information according to the subjects and schedule entered by the student.

Example:

```text
Monday       Android Development     09:00 - 10:00
Tuesday      Machine Learning        10:00 - 11:00
Wednesday    Cryptography             11:00 - 12:00
Thursday     Computer Networks        09:00 - 10:00
Friday       Software Engineering     10:00 - 11:00
```

The title **AI Timetable Generator** is used for the assignment. The basic version uses simple logic rather than a complex machine-learning model.

## UI Design
The application uses:
- Blue primary color
- White cards
- Light background
- Clear headings
- Simple navigation
- ConstraintLayout positioning

## Validation
Basic validation is included:
- Subject name cannot be empty.
- A message is displayed when required information is missing.
- Valid information can be submitted.

## Requirements

### Software
- Android Studio
- JDK 21
- Kotlin
- Android SDK
- Gradle

### Hardware
A normal computer capable of running Android Studio is sufficient. Testing can be done using an Android Emulator or physical Android phone.

## How to Run

1. Open Android Studio.
2. Select **Open** and choose the project folder.
3. Wait for Gradle Sync to complete.
4. Select an emulator or connect an Android phone.
5. Click **Run ▶**.
6. The application opens on the Home Screen.

## JVM/Gradle Configuration

The project uses JVM 21 for both Java and Kotlin.

Check:

```text
Settings
→ Build, Execution, Deployment
→ Build Tools
→ Gradle
→ Gradle JDK
```

Select **JDK 21**.

If required:

```text
File → Sync Project with Gradle Files
Build → Clean Project
Build → Rebuild Project
```

## Assignment Relevance

This project demonstrates:
- Android application development
- XML UI design
- ConstraintLayout
- Kotlin programming
- Activity navigation
- User input
- Event handling
- Basic validation
- Timetable management

## Future Improvements
Possible future features:
- Local data storage
- Timetable conflict detection
- Study-time planning
- Notifications and reminders
- Subject-wise progress tracking
- Edit and delete subjects
- More advanced timetable generation

## Author

**Project:** AI Timetable Generator  
**Course:** Mobile Application Development  
**Platform:** Android  
**Language:** Kotlin  
**UI:** XML + ConstraintLayout

## License

This project is created for educational and academic purposes.
