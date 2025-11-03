# **Lee Yi Sheng \- Project Portfolio Page**

## **Project: MAMA**

**MAMA** is a Command Line Interface (CLI) health-tracking application designed specifically for mothers to easily log and monitor crucial aspects of their postpartum journey, including meals, workouts, milk production, weight, and body measurements. My contributions focused on enhancing data visibility and improving the application's overall structure for better maintainability.

## **Summary of Contributions**

### **Code Contributed \[[RepoSense Code Dashboard](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=lee-yisheng&breakdown=true&sort=groupTitle+dsc&sortWithin=title&since=2025-09-19T00:00:00&timeframe=commit&mergegroup&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName)\]**

### **Enhancements Implemented**

1. **List Command and enhanced with Type Filtering:**
    * **What it does:** Allows users to filter the displayed entries by type (e.g., list /t meal, list /t workout). Previously, users could only view all entries.
    * **Justification:** This feature significantly improves usability, especially as the number of entries grows. Users can quickly find specific types of records without manually scanning the entire list.
    * **Highlights:**
        * Implemented a dedicated ListCommandParser to handle the parsing logic for the list command, adhering to the Single Responsibility Principle (SRP).
        * Refactored ListCommand to accept a Predicate\<Entry\>, making the filtering logic flexible and easily extensible for potential future filters (e.g., by date).
        * Ensured backward compatibility: list with no arguments still shows all entries.
        * Added comprehensive JUnit tests using a StorageStub to ensure test isolation from the file system.
        * Included robust assertions and logging for better maintainability.
2. **New Feature: dashboard Command:**
    * **What it does:** Provides a consolidated, at-a-glance view of the user's health status, including today's calorie intake vs. goal, today's total milk pumped, and this week's workout minutes vs. goal.
    * **Justification:** Addresses the user need to see the "bigger picture" of their health across different metrics without needing to run multiple list commands. This provides immediate, actionable insights.
    * **Highlights:**
        * Designed with high cohesion by abstracting logic into three distinct components:
            * DashboardSummary (Model): Handles all data aggregation and calculations.
            * DashboardFormatter (UI): Responsible solely for formatting the summary data into a user-friendly string.
            * ViewDashboardCommand (Control): Acts as a simple orchestrator.
        * This abstraction adheres strongly to SRP and makes the feature easy to test and modify (e.g., changing the dashboard layout only requires editing the DashboardFormatter).
        * Added logging and assertions to all relevant classes.
        * Included JUnit tests with a StorageStub to ensure correctness and isolation.
3. **Refactoring for Cohesion and Extensibility (OCP):**
    * **What it does:** Introduced EntryType and suggested CommandType enums to teammate Yu Jie to serve as single sources of truth for entry type definitions/parsing and command usage strings, respectively. Refactored ListCommandParser, Entry.fromStorageString, and Ui to utilize these enums.
    * **Justification:** The previous design scattered type and command information across multiple classes (Parser, ListCommandParser, Entry, Mama), violating SRP and the Open/Closed Principle. Adding new entry types or commands required modifying multiple files, increasing maintenance burden and risk of bugs.
    * **Highlights:**
        * Significantly improved code cohesion by centralizing related logic within the enums.
        * Made the system highly extensible. Adding a new Entry type now requires only modifying the EntryType enum; the parsers and UI update automatically.
        * Cleaned up the Mama.main() method by abstracting UI messages into the Ui class.

### **Contributions to Team-Based Tasks**

* Participated actively in weekly team meetings, contributing to feature planning and task allocation, ensuring that everything is moving accordingly.
* Helped set up initial project structure (github, team repo, organization repo, forking workflow)
* Automated initial V2 user stories making with a shell script

### **Review/Mentoring Contributions**

* Paired with Yu Jie to refactor **CommandResult** refactoring
* Reviewed PR \# 47 (​​Add Weight) : Helped Ryan solve gradle test fail issue on formatting (add newline after end of file) [https://github.com/AY2526S1-CS2113-T11-3/tp/pull/47](https://github.com/AY2526S1-CS2113-T11-3/tp/pull/47)
* Reviewed PR \#56 (refactor, commandException) : Provided feedback on levels of abstraction (violating SRP and SLAP), suggested refactoring [https://github.com/AY2526S1-CS2113-T11-3/tp/pull/56](https://github.com/AY2526S1-CS2113-T11-3/tp/pull/56)

### **Contributions to the User Guide (UG)**

* Added the section explaining the new dashboard command, including format, example usage, and output interpretation.
* Enhanced the existing list command section to accurately describe the new filtering functionality (/t TYPE prefix) and updated the list of valid TYPE values.

### **Contributions to the Developer Guide (DG)**

* Added the design and implementation details for the List Command Filter feature (Section 3.1 in DeveloperGuide.md), including the rationale, flow of control, and sequence diagram link.  
  (Self-note: Ensure the sequence diagram for List Command is added/updated in the DG)
* Added the design and implementation details for the Dashboard Command feature, explaining the DashboardSummary, DashboardFormatter, and DashboardCommand components and their interaction (Section 3.8 in DeveloperGuide.md).
* Briefly documented the rationale and impact of the **EntryType Enum refactoring** within the relevant feature sections or in a general design principles section.
* Added the design and implementation details for the Help Command feature.
