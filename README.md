[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/xYOl6zeZ)

# Codeventure - coding game
Group Assignment 2IRR00 2025 <Group 58>
## Table of contents
* [Introduction](#introduction)
* [Technologies](#technologies)
* [Installation](#installation)
* [Use Case Reference](#use-case-reference)
* [Design Patterns](design-patterns)
* [Intermediate Submissions](intermediate-submissions)
* [License](license)
* [Project Status](project-status)

## Introduction
Codventure is a 2D educational game designed to teach programming basics in an interactive way. Players control a character on a grid-based map (minimum size 4×4) by writing simple Java code. The objective is to guide the character from a start point to an endpoint while avoiding obstacles and meeting level-specific challenges. The game features a sandbox environment for executing code, encrypted JSON save files, visual feedback, and documentation to support the player’s learning.

## Technologies
this project is created using:
* Java (JDK 21)
* JavaFX

## Installation
Follow these steps to set up and run the game:
1. **Clone the repository**
   ```bash
    git clone https://github.com/TUe-MCS-2IRR00/2irr00-2025-group-assignment-graded-assignment-2irr00-2025-58.git
2. **Open the project in your preferred Java IDE** (e.g., Intellij IDEA, Eclipse).
3. **Set the project SDK to Java 21.**
4. **Run the main class** (i.e., `App.java`)

## Authors and acknowledgment
* **Dimitar Zhekov** — _Student ID: 2132966_ — `mitkozh` — d.z.zhekov@student.tue.nl
* **Sofia Constantinou** — _Student ID: 2127326_ — `sofiaconst` — s.c.constantinou@student.tue.nl
* **Stefanos Kritikos** — _Student ID: 2153785_ — `stefkrit27` — s.kritikos@student.tue.nl
* **Alex Christou** — _Student ID: 2075407_ — `alexchristou06` — a.christou@student.tue.nl
* **Efe Koç** — _Student ID: 2098156_ — `techinesis` — e.koc@student.tue.nl
* **Nicole Almeida** —  _Student ID: 2087480_ — `nicolealm1405` —  n.e.almeida@student.tue.nl

## Use Case Reference
The updated use case can be found in `placehodler`.  
It describes the step-by-step execution flow, including two alternative/exception scenarios.

## Design Patterns  
We applied the following design patterns in our project:
* **Observer Pattern**  
Used to notify parts of the UI or logic when game state changes (e.g., level won/lost, grid updates).
* **Singleton Pattern**  
So only one instance of core services (like `AudioManagerServiceImpl`, `NavigationManager`, `LevelServiceImpl`) exists and provides a global access point.
* **DTO (Data Transfer Object) Pattern**  
`LevelDTO` and similar classes are used to transfer structured data between layers (e.g., from service to controller) without exposing internal details.
* **MVC (Model-View-Controller) Pattern**  
Separates application logic (`Model`), UI (`View`), and user interaction handling (`Controller`) for better organization and maintainability.
* **Facade Pattern**  
`GameScreenServiceFacade` provides a simplified interface to complex subsystems, making it easier for controllers to interact with multiple services.
* **Service Locator Pattern**  
`GameServiceManager` acts as a registry to provide and manage access to various services needed throughout the application.

## Intermediate Submissions
You can find our intermediate submissions here:
* [Placeholder submission 1]
* [Placeholder submission 2]
* [Placeholder submission 3]

## License
This project was developed as a part of a group assignment for the 2IRR00 course at TU/e.  
It is intended for academic use only and is not licensed for commercial distribution.

## Project Status
This project has been completed and submitted as part of the 2IRR00 course at TU/e.  
No further updates are planned.
