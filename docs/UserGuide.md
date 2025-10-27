# User Guide

---

## Introduction

**Mama** is a Command Line Interface (CLI) health-tracking application designed for mothers to log meals, workouts, milk
pumping sessions, and body measurements.  
It helps users monitor nutrition, exercise, and postpartum recovery — all stored **locally** for privacy.

Mama is lightweight, works entirely **offline**, and runs on any system with Java 17 or later.

---

## Quick Start

1. **Ensure Java 17 or above is installed.**  
   You can verify by running:

```java -version```

2. **Download** the latest `mama.jar` file from your GitHub release page.

3. Open a terminal in the folder containing `mama.jar`.

4. Run the program:

```java -jar mama.jar```

5. You should see a welcome message and command prompt:

> Welcome to Mama! What would you like to do today?

6. Type a command and press **Enter** to start logging your activities.

---

## Features

This section explains each command in Mama.  
Features marked with *Coming soon* are not yet implemented in this version.

---

### 1. Viewing Entries — `list`

Displays all entries stored in the system.  
You can also filter entries by their type.

**Format**

> list
> list /t TYPE


**Valid TYPE values:**  
`meal`, `workout`, `milk`, `measure`, `weight`

**Examples**

```list```
```list /t meal```
```list /t workout```

**Notes**

- The index displayed beside each entry is **1-based**.
- Use that index when deleting an entry.

---

### 2. Deleting an Entry — `delete`

Removes an entry by its index as shown in the `list` command.

**Format**
> delete INDEX


**Examples**
```delete 2```
```delete 5```

**Expected Output**
> Deleted entry: MEAL | breakfast | 500 | 2025-10-20


**Error Cases**

- If the index is invalid or out of range, Mama will display  
  `Invalid index: Please provide a valid entry number.`

---

### 3. Logging a Meal — `meal`

Adds a meal entry with its calorie value.

**Format**
> meal MEAL_NAME /cal CALORIES


**Examples**
```meal breakfast /cal 500```
```meal chicken rice /cal 650```

**Notes**

- `CALORIES` must be a positive integer.
- Meals are stored with the current date by default.

---

### 4. Logging a Workout — `workout`

Adds a workout entry with its duration (in minutes).

**Format**
> workout TYPE /dur DURATION


**Examples**
```workout yoga /dur 30```
```workout run /dur 45```

**Notes**

- `DURATION` must be a positive integer.
- *Coming soon:* view a summary of weekly workout durations.

---

### 5. Managing Workout Goals — `workout goal`

Sets or views your **weekly workout goal**.

**Formats**
> workout goal MINUTES → sets a new goal
> workout goal → views current goal


**Examples**
```workout goal 150```
```workout goal```

**Notes**

- The goal represents total minutes of workouts per week.
- *Coming soon:* automatic progress bar and goal reminders.

---

### 6. Logging Milk Volume — `milk`

Records a milk-pumping session in millilitres (ml).

**Format**
> milk VOLUME


**Examples**
```milk 150```
```milk 80```

**Notes**

- `VOLUME` must be a non-negative integer.

---

### 7. Logging Weight — `weight`

Adds a body-weight entry in kilograms.

**Format**
> weight VALUE


**Examples**
```weight 70```
```weight 63```

**Notes**

- Only whole numbers are supported.  
  *Coming soon:* support for decimal values.
- Use `weight ?` (if supported in your version) to preview previous records.

---

### 8. Logging Body Measurements — `measure`

Records body measurements such as waist, hips, or chest.  
You can include any combination of available fields.

**Format**
> measure waist/WAIST hips/HIPS [chest/CHEST] [thigh/THIGH] [arm/ARM]


**Examples**
```measure waist/78 hips/92```
```measure waist/78 hips/92 chest/96 thigh/55 arm/30```

**Notes**

- Each field must be a positive integer (in cm).
- The order of fields does not matter.

---

### 9. Calorie Goal — `goal`

Sets or displays your daily calorie goal.

**Formats**
> goal CALORIES → sets a new goal
> goal → views current goal


**Examples**
```goal 1800```
```goal```

**Notes**

- Calories are summed from logged meals.
- *Coming soon:* automatic goal tracking and progress report.

---

### 10. Exiting the Program — `bye`

Ends the session and saves all data automatically.

**Format**
> bye


**Example**
```bye```

**Expected Output**
> Bye. Hope to see you again soon!


---

## Data File

Mama automatically creates and updates a text file named `mama.txt` in the same folder as `mama.jar`.  
Each entry is stored on a separate line, using the `|` character as a separator.

**Example Content**
> MEAL|breakfast|500|2025-10-20
> WORKOUT|yoga|30|2025-10-21
> MILK|150|2025-10-21
> MEASURE|weight|70|2025-10-26


**Notes**

- Do **not** edit `mama.txt` manually unless necessary.
- Deleting the file will reset all data.

---

## FAQ

**Q:** Can I log decimals for weight or milk volume?  
**A:** Not yet. Only whole numbers are supported. *(Coming soon)*

**Q:** How do I filter only specific entries?  
**A:** Use `list /t TYPE`. Example: `list /t meal`.

**Q:** Why do I get an invalid index error?  
**A:** The index number must exist in the list shown by `list`.

**Q:** Can I undo a deletion?  
**A:** Not in this version. *(Coming soon: Undo/Redo feature.)*

**Q:** Does Mama require internet access?  
**A:** No. Everything is stored locally for privacy.

---

## Command Summary

| Command             | Format                                                                | Example                    |
|---------------------|-----------------------------------------------------------------------|----------------------------|
| **List**            | `list` or `list /t TYPE`                                              | `list /t meal`             |
| **Delete**          | `delete INDEX`                                                        | `delete 2`                 |
| **Add Meal**        | `meal MEAL_NAME /cal CALORIES`                                        | `meal breakfast /cal 500`  |
| **Add Workout**     | `workout TYPE /dur DURATION`                                          | `workout yoga /dur 30`     |
| **Workout Goal**    | `workout goal [MINUTES]`                                              | `workout goal 150`         |
| **Add Milk**        | `milk VOLUME`                                                         | `milk 150`                 |
| **Add Weight**      | `weight VALUE`                                                        | `weight 70`                |
| **Add Measurement** | `measure waist/WAIST hips/HIPS [chest/CHEST] [thigh/THIGH] [arm/ARM]` | `measure waist/78 hips/92` |
| **Calorie Goal**    | `goal [CALORIES]`                                                     | `goal 1800`                |
| **Exit**            | `bye`                                                                 | `bye`                      |

---

## Coming Soon

- Undo/Redo functionality
- Multi-index deletion (e.g., `delete 2 3 4`)
- Graphical summaries (weekly progress, BMI charts)
- Integration with wearable devices
- Decimal support for weight and milk volume

---

## Notes on PDF Conversion

When finalising your submission:

- Save this page as **PDF from the GitHub Pages view**, following the module’s guide exactly.
- Ensure all **hyperlinks work** in the PDF.
- Keep image/screenshot resolution reasonable — final PDF must be **≤ 15 MB**.
- Avoid expandable panels or GIFs as they are not PDF-friendly.

