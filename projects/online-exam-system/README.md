# Online Exam System

A Java object-oriented model for creating exams, registering students, recording answers, and generating scores and grade reports. It separates question behavior from the manager that stores exams and student submissions.

## What it supports

- Create exams and add **true/false**, **multiple-choice**, and **fill-in-the-blank** questions.
- Register students and record answers by exam and question.
- Compute exam scores, grading reports, and per-exam minimum/maximum/average scores.
- Calculate course-level numeric and letter grades using configurable cutoffs.
- Save and restore manager state using Java object serialization.

## Code structure

- [`Manager.java`](src/onlineTest/Manager.java) — required management interface.
- [`SystemManager.java`](src/onlineTest/SystemManager.java) — implementation of exam/student storage, grading, reports, and persistence.
- [`Exam.java`](src/onlineTest/Exam.java), [`Student.java`](src/onlineTest/Student.java), and [`Submission.java`](src/onlineTest/Submission.java) — primary data models.
- [`Question.java`](src/onlineTest/Question.java) and the question subclasses — question types and grading behavior.

## Compile

This is a project library rather than a packaged UI or web service. From this project directory, with a JDK:

```bash
javac -d out $(find src -name '*.java')
```

Explore `SystemManager.java` to see how the methods in the `Manager` interface are implemented.

**Technical focus:** interfaces, inheritance, polymorphism, collections, serialization, and modular object design.

**Validation note:** Some previously saved outputs did not match reference results. The project is shared to demonstrate the design and implementation, **not** as a fully validated grading application.

> University of Maryland Java coursework. The assignment interfaces and some starter structure originated from the course.
