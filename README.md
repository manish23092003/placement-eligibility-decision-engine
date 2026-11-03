# Placement Eligibility Decision Engine

A modular, rule-based placement eligibility decision engine built in Java using core Object-Oriented Programming (OOP) principles, adhering to the KodNest Module 6 (Abstraction and Interfaces) specifications.

---

## 📌 Project Overview

The **Placement Eligibility Decision Engine** evaluates whether a candidate meets the eligibility criteria for dynamic campus placement job openings. Candidates and job listings are configured at runtime, and eligibility is determined across four extensible eligibility rules via polymorphic rule execution.

---

## 🏗️ Object-Oriented Programming (OOP) Concepts

This project demonstrates core Java OOP design principles:

1. **Encapsulation**:
   - `Candidate` and `Job` classes encapsulate all data using `private` member variables.
   - Access to internal state is strictly regulated via public getter methods and helper methods (e.g., `candidate.hasSkill()`).

2. **Abstraction**:
   - `EligibilityRule` is an `abstract class` that defines a clear, minimal contract:
     - `public abstract String getRuleName();`
     - `public abstract boolean check(Candidate candidate, Job job);`
   - Common rule abstractions isolate the business validation logic from rule evaluation.

3. **Inheritance**:
   - Concrete rule classes (`PercentageRule`, `BacklogRule`, `GraduationYearRule`, and `SkillRule`) extend `EligibilityRule`, inheriting the common rule contract.

4. **Method Overriding**:
   - Each child rule provides its own specific implementation of `getRuleName()` and `check(Candidate candidate, Job job)`.

5. **Polymorphism**:
   - `EligibilityChecker` evaluates rules polymorphically through an array of the parent type (`EligibilityRule[]`). The caller invokes `check()` on each rule without knowing the specific rule implementation at compile time.

6. **HAS-A Relationship (Composition)**:
   - `EligibilityChecker` HAS-A collection of `EligibilityRule` objects stored in an array: `private EligibilityRule[] rules`.

---

## 📋 Eligibility Rules

| Rule | Evaluation Criteria | Result if Met |
|---|---|---|
| **Percentage Rule** | Candidate percentage $\ge$ Minimum percentage required by job | PASS |
| **Backlog Rule** | Candidate backlogs $\le$ Maximum backlogs allowed by job | PASS |
| **Graduation Year Rule** | Candidate graduation year $==$ Required graduation year | PASS |
| **Skill Rule** | Candidate possesses all required skills specified by job | PASS |

A candidate is marked **`ELIGIBLE`** only if **all** rules pass. If any rule fails, the final result is **`NOT ELIGIBLE`**.

---

## 📂 Project Structure

```text
placement-eligibility-decision-engine/
├── src/
│   ├── BacklogRule.java          # Concrete rule verifying active backlogs
│   ├── Candidate.java            # Encapsulated candidate model
│   ├── EligibilityChecker.java   # Rule engine executing rules polymorphically
│   ├── EligibilityRule.java      # Abstract base class defining rule contract
│   ├── GraduationYearRule.java   # Concrete rule verifying graduation year
│   ├── Job.java                  # Encapsulated job opening model
│   ├── Main.java                 # Entry point: dynamic inputs and display flow
│   ├── PercentageRule.java       # Concrete rule verifying academic percentage
│   └── SkillRule.java            # Concrete rule verifying mandatory technical skills
├── .gitignore                    # Ignores compiled .class files
└── README.md                     # Project documentation
```

---

## 🚀 How to Compile and Run

### 1. Compile Source Code
From the root directory of the project:
```powershell
javac src/*.java
```

### 2. Run Application
```powershell
java -cp src Main
```

---

## 📥 Input Format

All candidate and job information is provided dynamically through runtime input:

1. **Candidate Details**:
   - `name` (String)
   - `percentage` (double)
   - `backlogs` (int)
   - `graduationYear` (int)
   - `skillCount` (int)
   - Followed by `skillCount` skill strings.

2. **Job Listings**:
   - `jobCount` (int)
   - For each job:
     - `company` (String)
     - `role` (String)
     - `minimumPercentage` (double)
     - `maximumBacklogs` (int)
     - `requiredGraduationYear` (int)
     - `requiredSkillCount` (int)
     - Followed by `requiredSkillCount` skill strings.

3. **Job Selection**:
   - `selectedJobNumber` (int, 1-based index corresponding to the listed available jobs).

---

## 🧪 Test Scenarios & Results

### Scenario 1: Everything Passes
- **Inputs**: Percentage: 75.0 (Min: 65.0), Backlogs: 0 (Max: 0), Year: 2026 (Req: 2026), Skills: Java, SQL. Selected Job: 1.
- **Rule Evaluations**:
  - `Percentage : PASS`
  - `Backlogs : PASS`
  - `Graduation Year : PASS`
  - `Skills : PASS`
- **Final Result**: `ELIGIBLE`

### Scenario 2: Percentage Fails
- **Inputs**: Percentage: 60.0 (Min: 65.0), Backlogs: 0, Year: 2026, Skills: Java, SQL. Selected Job: 1.
- **Rule Evaluations**:
  - `Percentage : FAIL`
  - `Backlogs : PASS`
  - `Graduation Year : PASS`
  - `Skills : PASS`
- **Final Result**: `NOT ELIGIBLE`

### Scenario 3: Backlogs Fail
- **Inputs**: Percentage: 75.0, Backlogs: 1 (Max: 0), Year: 2026, Skills: Java, SQL. Selected Job: 1.
- **Rule Evaluations**:
  - `Percentage : PASS`
  - `Backlogs : FAIL`
  - `Graduation Year : PASS`
  - `Skills : PASS`
- **Final Result**: `NOT ELIGIBLE`

### Scenario 4: Graduation Year Fails
- **Inputs**: Percentage: 75.0, Backlogs: 0, Year: 2025 (Req: 2026), Skills: Java, SQL. Selected Job: 1.
- **Rule Evaluations**:
  - `Percentage : PASS`
  - `Backlogs : PASS`
  - `Graduation Year : FAIL`
  - `Skills : PASS`
- **Final Result**: `NOT ELIGIBLE`

### Scenario 5: Skill Rule Fails
- **Inputs**: Candidate Skills: Java (Job requires Java and SQL). Selected Job: 1.
- **Rule Evaluations**:
  - `Percentage : PASS`
  - `Backlogs : PASS`
  - `Graduation Year : PASS`
  - `Skills : FAIL`
- **Final Result**: `NOT ELIGIBLE`

### Scenario 6: Selecting a Different Job
- **Inputs**: Candidate has Python skill. Job 1 requires Java; Job 2 requires Python. User selects Job 2.
- **Rule Evaluations**:
  - All rules evaluated against Job 2 pass.
- **Final Result**: `ELIGIBLE`

### Scenario 7: Boundary Percentage
- **Inputs**: Percentage: 65.0 (Exact minimum required: 65.0).
- **Rule Evaluations**:
  - `Percentage : PASS` ($\ge 65.0$)
  - `Backlogs : PASS`
  - `Graduation Year : PASS`
  - `Skills : PASS`
- **Final Result**: `ELIGIBLE`
