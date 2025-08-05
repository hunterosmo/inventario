-- Crear base de datos
CREATE DATABASE IF NOT EXISTS control;
USE control;

-- Crear tabla Area
CREATE TABLE Area (
    idArea INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Crear tabla Usuarios
CREATE TABLE Usuarios (
    idUsuarios INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(45) NOT NULL,
    clave VARCHAR(300) NOT NULL,
    nombre VARCHAR(100),
    correo VARCHAR(100),
    telefono VARCHAR(45),
    rol VARCHAR(20)
);

-- Crear tabla Inventarios con nombreProducto directo
CREATE TABLE Inventarios (
    idInventarios INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    nombreProducto VARCHAR(200) NOT NULL,
    idArea INT,
    cantidad DOUBLE,
    peso DOUBLE,
    precio DOUBLE,
    idUsuarios INT,
    FOREIGN KEY (idArea) REFERENCES Area(idArea),
    FOREIGN KEY (idUsuarios) REFERENCES Usuarios(idUsuarios)
);