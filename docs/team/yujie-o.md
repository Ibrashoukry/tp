# **Ong Yu Jie — Project Portfolio Page**

## **Project: MAMA**

**MAMA** is a Command Line Interface (CLI) health-tracking application designed for mothers to log and monitor essential
aspects of their daily wellbeing — including meals, workouts, milk production, weight, and body measurements.  
My main contributions focused on developing the **Body Measurement** and **Delete** features, **refactoring timestamp
handling**, and establishing the **foundational codebase architecture** (Parser–Command–Storage system). I also led the
creation of the **User Guide (UG)** and **Developer Guide (DG)** templates and coordinated **testing and documentation
consistency** across the team.

---

## **Summary of Contributions**

### **Code Contributed**

[**RepoSense Code Dashboard**](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=yujie-o&breakdown=true&sort=groupTitle+dsc&sortWithin=title&since=2025-09-19T00:00:00&timeframe=commit&mergegroup&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName)

---

### **Enhancements Implemented**

#### **1. New Feature: AddMeasurementCommand (Body Measurement)**

* **What it does:**  
  Records a user’s body measurements — **waist** and **hips** (required) with optional **chest**, **thigh**, and **arm**
  fields — in centimeters. Each record is timestamped and saved persistently.

* **Justification:**  
  Enables users to track physical changes throughout their postpartum journey, providing meaningful insights into
  recovery and fitness progress.

* **Highlights:**
    * Implemented `AddMeasurementCommand` and `BodyMeasurementEntry` classes.
    * Integrated the new entry type (`MEASURE`) into the parser and storage system.
    * Validated required parameters (`waist` and `hips`) and ensured robust error handling.
    * Used `TimestampedEntry` and `DateTimeUtil` to ensure all entries record timestamps in `dd/MM/yy HH:mm` format.
    * Added JUnit tests for successful command execution, missing argument handling, and persistence consistency.

---

#### **2. New Feature: DeleteCommand**

* **What it does:**  
  Deletes an entry from the currently displayed list using the **1-based index** shown by the most recent `list`
  command.  
  It also supports deletion from **filtered lists** created using `list /t TYPE` (e.g., `list /t meal`), ensuring that
  the deleted entry is correctly removed from the main `EntryList` and persisted in storage.

* **Justification:**  
  Provides users with the ability to remove unwanted entries efficiently while maintaining consistency with the list
  view.

* **Highlights:**
    * Implemented `DeleteCommand` to operate directly on the **filtered (shown) list** maintained by `EntryList`.
    * Added robust validation and clear error feedback when an invalid index is provided.
    * Ensured integration with `Storage#save()` for persistent deletion.
    * Added comprehensive unit and integration tests for both valid and invalid cases.

---

#### **3. Refactoring: Timestamp and Entry Infrastructure**

* **What it does:**  
  Centralized and standardized timestamp creation and formatting across all command types.

* **Justification:**  
  The initial codebase had inconsistent timestamp logic embedded across multiple commands. Consolidating this logic
  improves maintainability and ensures consistent date-time handling for all entries.

* **Highlights:**
    * Introduced `DateTimeUtil` and refactored `TimestampedEntry` to unify timestamp generation and formatting.
    * Updated all entry classes (Milk, Workout, Measurement) to extend `TimestampedEntry`.
    * Simplified testing and debugging by ensuring predictable timestamp formatting.

---

#### **4. Core Architecture Setup**

* **What it does:**  
  Established the project’s **command-driven architecture** and standardized command result handling.

* **Highlights:**
    * Created the foundational `Parser`, `Command`, and `Storage` skeleton classes for the project.
    * Implemented the `CommandResult` design pattern:
        * Added `CommandResult` class (`feedbackToUser`, `isExit`).
        * Updated all commands to return `CommandResult` instead of raw strings.
        * Modified the main program loop to consume `CommandResult` and terminate based on `isExit`.
        * Adjusted all existing tests to assert on `CommandResult` fields.
    * This setup provided a consistent framework for all future feature implementations by teammates.

---

### **Contributions to the User Guide (UG)**

* Created the **User Guide content for v1**, covering all currently implemented features (e.g., add, list, milk,
  workout, meal, weight, and goal commands).
* Added new sections for the **Body Measurement** and **Delete** features, including detailed usage examples and index
  behavior explanations.
* Ensured all command descriptions followed a consistent format (command syntax, examples, and notes).
* Updated the **Command Summary Table** to reflect all implemented commands.

---

### **Contributions to the Developer Guide (DG)**

* Wrote the **base Developer Guide content and feature templates**, including the architecture, logic, model, and
  storage sections to align with the AB3 format.
* Added detailed explanation of **DeleteCommand** implementation:
    * Described step-by-step deletion process with index validation, removal, and data persistence.
    * Explained how it supports both full (`list`) and filtered (`list /t TYPE`) views.
    * Included a sequence diagram showing parsing, validation, and saving to storage.
    * Discussed design alternatives such as delete-by-index vs delete-by-keyword and their trade-offs.
* Authored **Body Measurement Feature** section:
    * Explained command parsing, validation of required and optional fields, and entry creation logic.
    * Described integration with `DateTimeUtil` for timestamp generation and storage persistence.
    * Added a sequence diagram showing the full execution flow.
    * Discussed design decisions for required vs optional measurement parameters.
* Added and standardized **diagram color legend** across the team for visual consistency:
    * 🟩 Command Logic = `#D1E7DD`
    * 🟨 Model = `#FFF3CD`
    * 🟥 Storage = `#F8D7DA`
    * 🟦 UI = `#CFE2FF`

---

### **Contributions to Team-Based Tasks**

* Actively participated in **team discussions and milestone planning**, ensuring development progress remained aligned
  with project goals.
* Performed **code reviews** for teammates’ features, giving constructive feedback on structure, coding style, and
  adherence to SLAP and assertion best practices.
* Coordinated team testing and ensured cross-feature compatibility after timestamp refactoring.
* Supported team integration during merges and Gradle build verifications.

---

### **Review / Mentoring Contributions**

* Reviewed PRs
* Helped teammates update their command classes to return `CommandResult`.
* Verified that their features were unaffected by the timestamp and command result refactors.
* Provided ongoing feedback on code readability, SLAP adherence, and test structure.

---

### **Contributions Beyond the Project Team**

* Reported timestamp formatting bugs in other teams’ projects during peer testing.
* Shared implementation notes on command refactoring and `CommandResult` design on the class discussion forum to assist
  other teams encountering similar design issues.

---
