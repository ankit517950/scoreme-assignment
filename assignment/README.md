# ScoreMe Advanced Systems Design Assignment

## Overview

This project implements a heuristic scheduling system for MSME credit pipeline tasks running on a shared compute cluster.

The scheduler assigns tasks to processing slots while satisfying:

- Conflict constraints
- Resource capacity constraints
- SLA window constraints

The implementation uses a Priority-Aware Conflict First Greedy heuristic.

---

# Tech Stack

- Java 17
- Maven
- Jackson Databind (JSON parsing)

---

# Project Structure

src/main/java
├── AssignmentApplication.java
├── algorithm/
├── model/
└── utils/

instances/
outputs/

---

# How To Run

## Compile

mvn compile

## Run

Run AssignmentApplication.java

Input file:

instance/input.json

Output file:

outputs/result.json

---

# Input Format

Example:

{
"tasks": ["T0", "T1"],
"conflicts": [[0,1]],
"resources": [
[4,16,1,1],
[8,32,2,1]
],
"capacities": [
[32,128,8,6],
[32,128,8,6]
],
"windows": [
[0,1],
[0,1]
],
"weights": [5,3],
"K": 2
}

---

# Output Format

{
"assignment": {
"T0": 0,
"T1": 1
},
"penalty": 12.5,
"runtimeMs": 5,
"feasible": true,
"violationReason": ""
}

---

# Algorithm Summary

1. Tasks are sorted by descending priority weight.
2. Each task is assigned to the first feasible slot.
3. The algorithm checks:
    - conflict constraints
    - resource capacities
    - SLA windows
4. If no feasible slot exists, the instance is marked infeasible.

---

# Complexity

Time Complexity:

O(n log n + nKE)

where:
- n = number of tasks
- K = number of slots
- E = number of conflict edges

---

# Author

Submitted for ScoreMe Hackathon