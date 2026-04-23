# StudySpace Finder System

# Description

StudySpace Finder is a command-line application that helps students find and reserve study rooms on campus. Users can manage buildings and rooms, make and cancel reservations with automatic conflict detection, and find the nearest available room matching their capacity, features, and time slot using Dijkstra's shortest path algorithm.


# Build and Run Instructions

In the terminal...

Step 1 — Navigate into the project folder

cd StudySpaceFinder

Step 2 — Compile:

javac studyspace/model/*.java studyspace/structures/*.java studyspace/services/*.java studyspace/app/*.java

Step 3 — Run:

studyspace.app.CliApp

*Sample data is used however users can enter their own additional buildings and rooms*
