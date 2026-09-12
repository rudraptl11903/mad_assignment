Timetable Management App

A simple **Mobile Application Development (MAD)** project made with Android Studio using Kotlin and XML.

The application allows a student to add subjects with teacher, day, time, and room details. The saved subjects are then displayed automatically in a **Monday-to-Saturday timetable**.

## Project Objective

The main objective of this project is to demonstrate basic Android application development concepts:

- Activities
- XML user interface
- Kotlin programming
- User input
- Form validation
- Intent navigation
- Spinner
- SharedPreferences
- JSON data storage
- Dynamic timetable generation

The project is designed to be simple and easy to understand for a college practical or viva.

---

## Features

- Add a new subject
- Enter teacher name
- Select a day from Monday to Saturday
- Enter start time manually
- Enter end time manually
- Enter room number
- Validate required fields
- Save subjects locally
- Display subjects day-wise
- Add multiple subjects
- View empty days as **No classes scheduled**
- Add more subjects from the timetable screen
- Works without an internet connection

---

## Application Flow

```text
        ┌─────────────────────────┐
        │      MainActivity       │
        │                         │
        │     ADD SUBJECT         │
        └────────────┬────────────┘
                     │
                     ▼
        ┌─────────────────────────┐
        │   AddSubjectActivity   │
        │                         │
        │ Subject Name            │
        │ Teacher Name            │
        │ Day                     │
        │ Start Time              │
        │ End Time                │
        │ Room Number             │
        │                         │
        │     ADD SUBJECT         │
        └────────────┬────────────┘
                     │
                     ▼
        ┌─────────────────────────┐
        │    TimetableActivity    │
        │                         │
        │ Monday                  │
        │ Tuesday                 │
        │ Wednesday               │
        │ Thursday                │
        │ Friday                  │
        │ Saturday                │
        └─────────────────────────┘
```

---

## Screens

### 1. Main Screen

**Activity:** `MainActivity.kt`  
**Layout:** `activity_main.xml`

The main screen contains:

- Project title: **Timetable Management**
- Short project description
- **ADD SUBJECT** button

Clicking **ADD SUBJECT** opens the Add Subject screen.

---

### 2. Add Subject Screen

**Activity:** `AddSubjectActivity.kt`  
**Layout:** `activity_add_subject.xml`

The user enters the following information:

| Field | Description |
|---|---|
| Subject Name | Name of the subject |
| Teacher Name | Name of the teacher |
| Day | Monday to Saturday |
| Start Time | Class starting time |
| End Time | Class ending time |
| Room Number | Classroom or laboratory number |

Example:

```text
Subject Name : Android Development
Teacher Name : Prof. Patel
Day          : Monday
Start Time   : 09:00 AM
End Time     : 10:00 AM
Room Number  : A-101
```

### Validation

The application checks the required fields before saving.

Possible messages include:

```text
Please enter subject name
Please enter teacher name
Please enter start time
Please enter end time
Please enter room number
```

After successful validation, the subject is saved and the timetable screen opens.

---

### 3. Timetable Screen

**Activity:** `TimetableActivity.kt`  
**Layout:** `activity_timetable.xml`

The timetable displays all six days:

```text
Monday
────────────────────────
Android Development
09:00 AM - 10:00 AM
Prof. Patel
Room: A-101

Tuesday
────────────────────────
Database Management
10:00 AM - 11:00 AM
Prof. Shah
Room: B-202

Wednesday
────────────────────────
No classes scheduled
```

The screen also provides:

- **Home** button
- **+ Add Subject** button

The timetable is generated dynamically from the subjects stored on the device.

---

## Data Storage

The project uses **SharedPreferences** for local storage.

No external database is used.

The data is stored as JSON.

Example structure:

```text
SharedPreferences
       │
       ▼
   JSON Array
       │
       ▼
    Subjects
       │
       ▼
TimetableActivity
```

The `SubjectRepository.kt` class handles:

- Getting saved subjects
- Saving subjects
- Adding a new subject
- Clearing subjects

---

## Project Files

```text
mad_Assignment/
│
├── app/
│   └── src/
│       └── main/
│           │
│           ├── AndroidManifest.xml
│           │
│           ├── java/
│           │   └── com/example/mad_assignment/
│           │       ├── MainActivity.kt
│           │       ├── AddSubjectActivity.kt
│           │       ├── TimetableActivity.kt
│           │       ├── Subject.kt
│           │       └── SubjectRepository.kt
│           │
│           └── res/
│               ├── drawable/
│               │   ├── bg_card.xml
│               │   ├── bg_light_blue.xml
│               │   ├── ic_launcher_background.xml
│               │   └── ic_launcher_foreground.xml
│               │
│               ├── layout/
│               │   ├── activity_main.xml
│               │   ├── activity_add_subject.xml
│               │   └── activity_timetable.xml
│               │
│               ├── values/
│               │   ├── colors.xml
│               │   ├── strings.xml
│               │   └── themes.xml
│               │
│               ├── values-night/
│               │   └── themes.xml
│               │
│               └── xml/
│                   ├── backup_rules.xml
│                   └── data_extraction_rules.xml
│
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

---

## Main Kotlin Classes

### MainActivity.kt

Responsible for the main screen.

```text
MainActivity
     │
     └── ADD SUBJECT
              │
              ▼
     AddSubjectActivity
```

### AddSubjectActivity.kt

Responsible for:

1. Reading user input
2. Validating the input
3. Creating a `Subject` object
4. Saving the subject
5. Opening the timetable screen

### Subject.kt

This is the data model for a subject.

It contains:

```text
name
teacher
day
startTime
endTime
room
```

### SubjectRepository.kt

Responsible for local subject storage using SharedPreferences and JSON.

Main functions:

```text
getSubjects()
saveSubjects()
addSubject()
clearSubjects()
```

### TimetableActivity.kt

Responsible for:

1. Loading saved subjects
2. Checking the selected day
3. Grouping subjects by day
4. Creating the timetable dynamically
5. Showing **No classes scheduled** for an empty day

---

## Technologies Used

| Technology | Use |
|---|---|
| Android Studio | Development IDE |
| Kotlin | Application logic |
| XML | User interface |
| ConstraintLayout | Screen layout |
| SharedPreferences | Local storage |
| JSON | Store subject data |
| Material Components | Android UI components |

---

## Requirements

- Android Studio
- Android SDK
- JDK
- Android emulator or physical Android phone

The application does not require an internet connection while it is running.

---

## How to Run

1. Extract the project ZIP.
2. Open **Android Studio**.
3. Select **File → Open**.
4. Select the `mad_Assignment` project folder.
5. Allow Gradle synchronization to complete.
6. Connect an Android phone or start an emulator.
7. Click **Run ▶**.
8. The application will open on the Main screen.

---

## How to Use

### Add a Subject

1. Open the application.
2. Click **ADD SUBJECT**.
3. Enter the subject name.
4. Enter the teacher name.
5. Select the day.
6. Enter the start time.
7. Enter the end time.
8. Enter the room number.
9. Click **ADD SUBJECT**.
10. The timetable will be displayed.

### Add Another Subject

From the timetable screen:

```text
+ Add Subject
```

can be selected to add another subject.

The timetable is refreshed when the user returns to the timetable screen.

---

## Example

### Input

```text
Subject Name : Mobile Application Development
Teacher Name : Prof. Shah
Day          : Monday
Start Time   : 09:00 AM
End Time     : 10:00 AM
Room Number  : A-101
```

### Output

```text
Monday
────────────────────────
Mobile Application Development
09:00 AM - 10:00 AM
Prof. Shah
Room: A-101
```

If no subject exists for a particular day:

```text
Wednesday
────────────────────────
No classes scheduled
```

---

## Offline Support

The application stores subject information locally using SharedPreferences.

Therefore:

- No server is required
- No API is required
- No Firebase is required
- No online account is required
- No internet connection is required for normal application use

---

## Academic Concepts Demonstrated

This project demonstrates the following Mobile Application Development concepts:

- Activity creation
- Activity navigation using Intent
- XML layouts
- ConstraintLayout
- EditText
- Button
- TextView
- Spinner
- Toast messages
- Data classes
- SharedPreferences
- JSON
- Dynamic UI creation
- Input validation
- Local data management

---

## Viva Questions

### What is the purpose of this project?

The purpose is to create a simple Android application that allows students to add subjects and view them in a day-wise timetable.

### Which programming language is used?

**Kotlin**.

### Which technology is used for the UI?

**XML with ConstraintLayout**.

### How many main activities are used?

There are three main activities:

```text
MainActivity
AddSubjectActivity
TimetableActivity
```

### How is data stored?

Data is stored locally using **SharedPreferences** in JSON format.

### Is a database used?

No. The project uses SharedPreferences instead of a database.

### Is Firebase used?

No.

### Is an API used?

No.

### Does the app need internet?

No.

### How does the timetable know which subject belongs to which day?

Each subject stores a `day` value. `TimetableActivity` compares that value with the six available days and displays the matching subjects.

### Can multiple subjects be added?

Yes. Each new subject is added to the locally saved subject list.

---

## Project Purpose

This project is intended for **Mobile Application Development (MAD)** academic work and demonstrates basic Android development using Kotlin, XML, Activities, local storage, and dynamic timetable generation.

---

## Author

**24012011122_RUDRA_PATEL**

Developed using:

```text
Android Studio
Kotlin
XML
ConstraintLayout
SharedPreferences
JSON
```
