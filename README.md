# mood-tracker

A pure Java application that runs in the console. Create and read mood entries.

### User Stories

- As a user I want to track my moods on a daily basis so that I have a
  record of it.

- As a user I want to be able to enter my mood data such as my current mood and the intensity of that mood and also put a description on that entry so that I have an accurate record of how I am feeling on any given day or time.

- As a user I want to be able to view my mood history so that I can see all of my mood entries.

- As a user I want to be able to update specific entries so that I have the flexibility to do so.

- As a user I want to be able to delete entries so that I can remove mood entries that I no longer want.

- As a user I want to filter entires based on different criteria like date, mood, and intensity.

### Implementation

I used an MVC architecture with a database layer to organise my code. The view takes input from the user and calls methods from the controller updating the models. In the database layer I used a H2 in-memory database to temporarily store the mood entries for demonstration purposes.

Throughout the application I have implemented fundamental and advanced features of the java programming language. From building blocks like classes, objects, inheritance, constructors, interfaces, overloading, overriding, access modifiers and exceptions to more advanced features like functional interfaces, lambdas, anonymous inner class, sealed class and records.

The app demonstrates the following Java features:

Fundamentals:

- classes
- contrast this() and this.
- method overloading
- varargs
- LVTI
- encapsulation
- interfaces
- inheritance
- overriding and polymorphism
- contrast super() and super.
- exceptions (checked and unchecked)
- enums
- arrays
- use of Java Core API (String, StringBuilder, List/ArrayList, Date API)

Advanced:

- call-by-value and defensive copying
- private, default and static interface methods
- records
- a custom immutable type
- lambdas (Predicate)
- discussion of ‘final’ or ‘effectively final’
- method references
- switch expressions and pattern matching
- sealed classes and interfaces

### Improvements

- Allow user to filter on different conditions like moods and intensity.
- Add functionality where a user can update a specific record by ID.
- Add functionality that would allow a user to delete a mood entry by ID.

### Run the App

Compile:

```bash
javac -d output -cp "lib/gson-2.11.0.jar:lib/h2-2.1.210.jar" \*/\*.java
```

Run:

```bash
java -cp "output:lib/gson-2.11.0.jar:lib/h2-2.1.210.jar" app.MainApp
```
