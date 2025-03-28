# Mood-Tracker

A pure Java application that runs in the console, enabling users to create and read mood entries.

---

## Implementation

This application is built using an **MVC (Model-View-Controller)** architecture combined with a database layer. Here’s how the components interact:

- **View:** Takes input from the user and calls methods from the controller.
- **Controller:** Updates the models and interacts with the database.
- **Database Layer:** Utilizes an H2 in-memory database to temporarily store mood entries for demonstration purposes.

### Key Features

The application showcases both fundamental and some more advanced features of the Java programming language.

#### **Fundamental Features:**

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

#### **Advanced Features:**

- Functional Programming:
  - Lambdas (e.g., `Predicate`)
  - Method references
  - Discussion of `final` and `effectively final` variables
- Modern Java Features:
  - Switch expressions and pattern matching
  - Records (custom immutable types)
  - Sealed classes and interfaces
- Interfaces:
  - Private, default, and static methods
- Defensive Coding:
  - Call-by-value and defensive copying

---

## Planned Improvements

1. **Filtering:**
   - Add functionality to filter mood entries based on conditions such as mood type and intensity.
2. **Updating Records:**
   - Enable users to update specific entries by their unique ID.
3. **Deleting Records:**
   - Allow users to delete a mood entry by its ID.

---

## Running the Application

### Build:

```bash
mvn clean install
```

### Run:

```bash
mvn exec:java -Dexec.mainClass="app.MainApp"
```
