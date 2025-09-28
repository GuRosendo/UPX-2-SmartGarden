DROP DATABASE IF EXISTS UPX_4_Urban_Garden;

CREATE DATABASE IF NOT EXISTS UPX_4_Urban_Garden;

USE UPX_4_Urban_Garden;

-- Tabela enderecos
CREATE TABLE addresses(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    CEP CHAR(8) NOT NULL,
    countryName VARCHAR(56) NOT NULL,
    cityName VARCHAR(58) NOT NULL,
    neighborhoodName VARCHAR(85) NOT NULL,
    streetName VARCHAR(50) NOT NULL,
    number VARCHAR(20) NOT NULL,
	type TINYINT CHECK (type IN (1, 2, 3)) NOT NULL COMMENT "1 - Usuario; 2 - Instituicao; 3 - Terreno",
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela instituicoes
CREATE TABLE institutions(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    institutionAddress INT NOT NULL,
    institutionName VARCHAR(255) NOT NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_institutions_institutionAddress FOREIGN KEY(institutionAddress) REFERENCES addresses(id) ON DELETE CASCADE
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela usuarios
CREATE TABLE users(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    userAddress INT NOT NULL,
    institution INT NULL, -- Preenchido somente quando o usuario faz parte de uma instituicao
    fullName VARCHAR(255) NOT NULL,
    userName VARCHAR(50) NOT NULL, 
    userEmail VARCHAR(255) NOT NULL,
    userPassword VARCHAR(255) NOT NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_userAddress FOREIGN KEY(userAddress) REFERENCES addresses(id) ON DELETE CASCADE,
    CONSTRAINT fk_users_userInstitution FOREIGN KEY(institution) REFERENCES institutions(id) ON DELETE CASCADE
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela terrenos livres
CREATE TABLE lands(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    landAddress INT NOT NULL,
    landName VARCHAR(255) NOT NULL,
	createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_lands_landAddress FOREIGN KEY(landAddress) REFERENCES addresses(id) ON DELETE CASCADE
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela instituicoes chefes dos terrenos
CREATE TABLE headInstitutionLands(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    institution INT NOT NULL,
    land INT NOT NULL,
	createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_headInstitutionLands_institution FOREIGN KEY(institution) REFERENCES institutions(id) ON DELETE CASCADE,
    CONSTRAINT fk_headInstitutionLands_land FOREIGN KEY(land) REFERENCES lands(id) ON DELETE CASCADE
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela tipos plantas
CREATE TABLE cropTypes(
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
	createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de plantios
CREATE TABLE plantings(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	headInstitutionLand INT NOT NULL,
    cropTypes INT NOT NULL,
    approximateHarvestDate DATETIME NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_plantings_headInstitutionLand FOREIGN KEY(headInstitutionLand) REFERENCES headInstitutionLands(id) ON DELETE CASCADE,
    CONSTRAINT fk_plantings_cropTypes FOREIGN KEY(cropTypes) REFERENCES cropTypes(id) ON DELETE CASCADE
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de voluntarios no terreno
CREATE TABLE volunteerPlantings(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    volunteer INT NOT NULL,
    headInstitutionLand INT NOT NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_volunteerPlantings_Volunteer FOREIGN KEY(volunteer) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_volunteerPlantings_headInstitutionLand FOREIGN KEY(headInstitutionLand) REFERENCES headInstitutionLands(id) ON DELETE CASCADE
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela custos plantacao
CREATE TABLE plantingCosts(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    headInstitutionLand INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT NULL,
    cost DECIMAL(10,2) NOT NULL,
	createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_plantingCosts_headInstitutionLand FOREIGN KEY(headInstitutionLand) REFERENCES headInstitutionLands(id) ON DELETE CASCADE
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de producoes da plantacao
CREATE TABLE landProductions(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    headInstitutionLand INT NULL,
    profit DECIMAL(10,2) NOT NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_landProductions_headInstitutionLand FOREIGN KEY(headInstitutionLand) REFERENCES headInstitutionLands(id) ON DELETE CASCADE
);