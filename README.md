# Mood Tracker Application

A console-based Java application that allows users to track their moods by creating, reading, filtering, and managing mood entries. The application is built using a **layered architecture** for better separation of concerns and maintainability.

---

## Architecture

The application follows a **layered architecture** with the following components:

- **Controller Layer:** Handles user requests and delegates tasks to the service layer.
- **Service Layer:** Contains the business logic for processing mood entries.
- **Repository Layer:** Interacts with the database to perform CRUD operations.
- **Database Layer:** Uses **MySQL** as the database for persistent storage.

---

## Key Features

### **Mood Entry Management**

- Add, edit, and delete mood entries.
- View all mood entries for a user.

### **Filtering and Grouping**

- Filter mood entries based on:
  - Intensity (greater, less, or equal to a threshold).
  - Date (before or after a specific date).
- Group mood entries by their date.

### **Advanced Stream Operations**

- Partition mood entries into two groups based on intensity.
- Map mood entry IDs to their descriptions.
- Sort mood entries by date.
- Display unique mood descriptions with a limit on the number of results.

### **Statistics**

- Count the total number of mood entries for a user.
- Find the mood entry with the highest intensity.
- Check if all mood entries match a specific condition.
- Find any mood entry that matches a condition.

---

## Java Features Demonstrated

This application showcases a wide range of Java features, including both fundamental and advanced concepts.

### **Fundamental Features:**

- **Object-Oriented Principles:**
  - Classes and objects
  - Encapsulation
  - Inheritance and polymorphism
  - Method overloading and overriding
- **Key Java Features:**
  - Access modifiers (e.g., `private`, `public`)
  - Enums
  - Arrays
  - Exceptions (checked and unchecked)
  - Core API usage: `String`, `StringBuilder`, `List/ArrayList`, and Date API
- **Special Constructs:**
  - `this()` vs. `this.`
  - `super()` vs. `super.`
  - Varargs
  - Local Variable Type Inference (LVTI)

### **Advanced Features:**

- **Functional Programming:**
  - Lambdas (e.g., `Predicate`)
  - Method references
  - Discussion of `final` and `effectively final` variables
- **Modern Java Features:**
  - Switch expressions and pattern matching
  - Records (custom immutable types)
  - Sealed classes and interfaces
- **Interfaces:**
  - Private, default, and static methods
- **Defensive Coding:**
  - Call-by-value and defensive copying

### **Stream API Operations:**

The application makes extensive use of the **Stream API** for functional programming and data processing:

- **Terminal Operations:**
  - `min()`, `max()`, `count()`, `findAny()`, `findFirst()`
  - `allMatch()`, `anyMatch()`, `noneMatch()`
  - `forEach()`
- **Collectors:**
  - `Collectors.toMap()`
  - `Collectors.groupingBy()`
  - `Collectors.partitioningBy()`

---

## Running the Application

### Prerequisites:

- Java 21 or later
- Maven 3.6 or later
- MySQL database installed and running

### Database Setup:

1. Create a MySQL database:
   ```sql
   CREATE DATABASE mood_tracker;
   ```
