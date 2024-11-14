# mood-tracker

A pure Java application that runs in the console. Create and read mood entries.

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

To compile:
javac -d output -cp "lib/gson-2.11.0.jar:lib/h2-2.1.210.jar" \*/\*.java

To run: java -cp "output:lib/gson-2.11.0.jar:lib/h2-2.1.210.jar" app.MainApp
