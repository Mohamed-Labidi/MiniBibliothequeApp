Mini-Bibliothèque 📚
    A professional Java Desktop application designed for library inventory management. This project implements a clean N-Tier architecture using the DAO (Data Access Object) pattern to bridge a Java Swing frontend with a MySQL relational database.

🛠 Tech Stack
    Language: Java (JDK 17+)

    GUI Framework: Java Swing / AWT

    Database: MySQL 8.0

    Connectivity: JDBC (Java Database Connectivity)

    Architecture: DAO Design Pattern

✨ Key Features
    Complete CRUD Lifecycle: Add, view, and delete book records with persistent storage.

    Real-time Live Search: An optimized search bar that filters the library collection instantly as you type (using KeyAdapter events).

    Automated Availability Tracking: Logic-driven status management for book inventory.

    Professional UI: System-native Look and Feel integration via UIManager.

    Robust Persistence: Decoupled database logic using PreparedStatement to prevent SQL injection and ensure data integrity.

📂 Project Structure
Plaintext
src/minibibliotheque/
├── Main.java                # Application entry point & UI Thread management
├── MiniBibliothequeUI.java  # Graphical Interface & Event Handling
├── Livre.java               # Entity Model (POJO)
├── LivreDAO.java            # Data Access Object (SQL Logic)
└── DatabaseConnection.java  # JDBC Connection Singleton

🚀 Getting Started
1. Database Setup
Create a MySQL database named minibibliotheque and execute the following script:

SQL
CREATE TABLE livres (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titre VARCHAR(255) NOT NULL,
    auteur VARCHAR(255) NOT NULL,
    annee_publication INT,
    isbn VARCHAR(20),
    disponible BOOLEAN DEFAULT TRUE
);
-- Table for the Agents (For Login/Authentication)
CREATE TABLE agents (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nom_complet VARCHAR(100)
);
-- Table pour les Clients (Lecteurs)
CREATE TABLE clients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telephone VARCHAR(20),
    adresse TEXT,
    date_inscription TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
2. Configuration
Update the credentials in DatabaseConnection.java:

Java
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
3. Execution
Compile the project (avoiding .class files in your commits).

Run Main.java.

🧠 Academic Context
    Developed as part of the SINFO (Computer Science) curriculum at the Faculty of Sciences Semlalia. The project focuses on mastering Object-Oriented Programming (OOP) principles and relational database integration.