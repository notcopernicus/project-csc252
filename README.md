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

java studyspace.app.CliApp

### Note

*Sample data is used however users can enter their own additional buildings and rooms with CSV data generated in /data*

*Disregard test folder is not part of the final submission. Please refer to StudySpaceFinder/ for the complete project.*

*Time values must be entered in 24-hour HH:MM format with leading zeros (e.g., 09:00 instead of 9:00).*
