-- Tabela enderecos
CREATE TABLE addresses(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    CEP CHAR(8) NOT NULL,
    country INT NOT NULL,
    state INT NOT NULL,
    city INT NOT NULL,
    neighborhoodName VARCHAR(85) NOT NULL,
    streetName VARCHAR(50) NOT NULL,
    number VARCHAR(20) NOT NULL,
	type TINYINT UNSIGNED NOT NULL COMMENT "1 - Usuario/Voluntario; 2 - Instituicao; 3 - Terreno",
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_addresses_country FOREIGN KEY(country) REFERENCES countries(id) ON DELETE CASCADE,
    CONSTRAINT fk_addresses_state FOREIGN KEY(state) REFERENCES states(id) ON DELETE CASCADE,
    CONSTRAINT fk_addresses_city FOREIGN KEY(city) REFERENCES cities(id) ON DELETE CASCADE,
    INDEX(type),
    INDEX(deletedAt),
    INDEX(createdAt)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela instituicoes
CREATE TABLE institutions(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    institutionAddress INT NOT NULL,
    institutionName VARCHAR(255) NOT NULL,
    institutionCNPJ VARCHAR(14) NOT NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_institutions_institutionAddress FOREIGN KEY(institutionAddress) REFERENCES addresses(id) ON DELETE CASCADE,
    INDEX(deletedAt),
    INDEX(createdAt)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela usuarios
CREATE TABLE users(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    userAddress INT NOT NULL,
    institution INT NULL, -- Preenchido somente quando o usuario faz parte de uma instituicao
    fullName VARCHAR(255) NOT NULL,
    userName VARCHAR(50) NULL, 
    userEmail VARCHAR(190) NOT NULL,
    userPassword VARCHAR(190) NULL,
	isAdmin TINYINT UNSIGNED NULL COMMENT "1 - Admin", 
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_userAddress FOREIGN KEY(userAddress) REFERENCES addresses(id) ON DELETE CASCADE,
    CONSTRAINT fk_users_userInstitution FOREIGN KEY(institution) REFERENCES institutions(id) ON DELETE CASCADE,
    INDEX(userEmail),
    INDEX(userPassword),
	INDEX(deletedAt),
    INDEX(createdAt)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela terrenos livres
CREATE TABLE lands(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    landAddress INT NOT NULL,
    landName VARCHAR(255) NOT NULL,
	createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_lands_landAddress FOREIGN KEY(landAddress) REFERENCES addresses(id) ON DELETE CASCADE,
    INDEX(deletedAt),
    INDEX(createdAt)
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
    CONSTRAINT fk_headInstitutionLands_land FOREIGN KEY(land) REFERENCES lands(id) ON DELETE CASCADE,
    INDEX(deletedAt),
    INDEX(createdAt)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela tipos plantas
CREATE TABLE cropTypes(
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    institution INT NOT NULL, 
    name VARCHAR(255) NOT NULL,
    seedingDateStart DATE NULL,
    seedingDateEnd DATE NULL,
	createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	CONSTRAINT fk_cropTypes_institution FOREIGN KEY(institution) REFERENCES institutions(id) ON DELETE CASCADE,
    INDEX(deletedAt),
    INDEX(createdAt)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de plantios
CREATE TABLE plantings(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	headInstitutionLand INT NOT NULL,
    cropType INT NOT NULL,
    approximatedHarvestDate DATE NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_plantings_headInstitutionLand FOREIGN KEY(headInstitutionLand) REFERENCES headInstitutionLands(id) ON DELETE CASCADE,
    CONSTRAINT fk_plantings_cropTypes FOREIGN KEY(cropType) REFERENCES cropTypes(id) ON DELETE CASCADE,
    INDEX(deletedAt),
    INDEX(createdAt)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de voluntarios no terreno
CREATE TABLE volunteerPlantings(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    volunteer INT NOT NULL,
    headInstitutionLand INT NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_volunteerPlantings_volunteer FOREIGN KEY(volunteer) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_volunteerPlantings_headInstitutionLand FOREIGN KEY(headInstitutionLand) REFERENCES headInstitutionLands(id) ON DELETE CASCADE,
    INDEX(deletedAt),
    INDEX(createdAt)
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
    CONSTRAINT fk_plantingCosts_headInstitutionLand FOREIGN KEY(headInstitutionLand) REFERENCES headInstitutionLands(id) ON DELETE CASCADE,
    INDEX(deletedAt),
    INDEX(createdAt)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de producoes da plantacao
CREATE TABLE plantingsProductions(
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    headInstitutionLand INT NULL,
    profit DECIMAL(10,2) NOT NULL,
    weight DECIMAL(10,2) NULL,
    finishedAt DATETIME NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deletedAt DATETIME NULL,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_landProductions_headInstitutionLand FOREIGN KEY(headInstitutionLand) REFERENCES headInstitutionLands(id) ON DELETE CASCADE,
    INDEX(deletedAt),
    INDEX(createdAt)
);