DROP TABLE IF EXISTS PingTable;
CREATE TABLE PingTable (foo CHAR(1));

SET foreign_key_checks=0;

DROP TABLE IF EXISTS
    ID_Gen, Account, PhysicalAccount, Employee, Client, LegalAccount, Provider, ProviderIngredient,
    Ingredient, Plate, PlateIngredient, Rol, RolAccount, Ordere, ClientOrder, Booking, BookingClient,
    Board, BookingBoard
;

SET foreign_key_checks=1;

-- ID_GEN
-- ******

CREATE TABLE ID_Gen (
    gen_name VARCHAR(80),
    gen_val BIGINT,
    CONSTRAINT pk_id_gen
        PRIMARY KEY (gen_name)
) ENGINE = InnoDB
;

CREATE TABLE Account (
    accountId BIGINT NOT NULL AUTO_INCREMENT ,
    username VARCHAR(255) NOT NULL ,
    password VARCHAR(255) NOT NULL ,
    salt VARCHAR(255) NOT NULL ,
    email VARCHAR(255) NOT NULL ,
    locale VARCHAR(255) NOT NULL ,
    timeZone VARCHAR(255) NOT NULL ,
    activated BIT NOT NULL ,
    activationCode VARCHAR(255) NOT NULL ,
    version BIGINT NOT NULL ,
    CONSTRAINT pk_account
        PRIMARY KEY (accountId)
)ENGINE = InnoDB
;

CREATE TABLE PhysicalAccount (
    accountId BIGINT NOT NULL ,
    name VARCHAR(255) NOT NULL ,
    direction VARCHAR(255) NOT NULL ,
    phoneNumber VARCHAR(255) NOT NULL
)ENGINE = InnoDB
;

CREATE TABLE LegalAccount (
    accountId BIGINT NOT NULL ,
    phoneNumber2 VARCHAR(255) NOT NULL ,
    fax VARCHAR(255) NOT NULL ,
    contactName VARCHAR(255) NOT NULL ,
    cif VARCHAR(255) NOT NULL ,
    web VARCHAR(255) NOT NULL
)ENGINE = InnoDB
;

CREATE TABLE Employee (
    accountId BIGINT NOT NULL ,
    salary DOUBLE PRECISION NOT NULL
)ENGINE = InnoDB
;

CREATE TABLE Client (
    accountId BIGINT NOT NULL
)ENGINE = InnoDB
;

CREATE TABLE Provider (
    accountId BIGINT NOT NULL
)ENGINE = InnoDB
;

CREATE TABLE ProviderIngredient (
    accountId BIGINT NOT NULL ,
    ingredientId BIGINT NOT NULL ,
    stock INT NOT NULL ,
    price DOUBLE PRECISION NOT NULL ,
    version BIGINT NOT NULL ,
    CONSTRAINT pk_providerIngredient
        PRIMARY KEY (accountId, ingredientId)
)ENGINE = InnoDB
;

CREATE TABLE Ingredient (
    ingredientId BIGINT NOT NULL AUTO_INCREMENT ,
    name VARCHAR(255) NOT NULL ,
    description VARCHAR(255) NOT NULL ,
    protein INT NOT NULL ,
    fat INT NOT NULL ,
    carbohydrates INT NOT NULL ,
    alcohol INT NOT NULL ,
    kcal INT NOT NULL ,
    version BIGINT NOT NULL ,
    CONSTRAINT pk_ingredient
        PRIMARY KEY (ingredientId)
)ENGINE = InnoDB
;

CREATE TABLE Plate (
    plateId BIGINT NOT NULL AUTO_INCREMENT ,
    name VARCHAR(255) NOT NULL ,
    description VARCHAR(255) NOT NULL ,
    price DOUBLE PRECISION NOT NULL ,
    version BIGINT NOT NULL ,
    CONSTRAINT pk_plate
        PRIMARY KEY (plateId)
)ENGINE = InnoDB
;

CREATE TABLE PlateIngredient (
    plateId BIGINT NOT NULL ,
    ingredientId BIGINT NOT NULL ,
    quantity INT NOT NULL ,
    version BIGINT NOT NULL ,
    CONSTRAINT pk_plateIngredient
        PRIMARY KEY (plateId, ingredientId)
)ENGINE = InnoDB
;

-- ROL
-- ***

CREATE TABLE Rol (
    rolId BIGINT NOT NULL AUTO_INCREMENT ,
    rolType VARCHAR(255) NOT NULL ,
    version BIGINT NOT NULL ,
    CONSTRAINT pk_rol
        PRIMARY KEY (rolId)
)ENGINE = InnoDB
;

CREATE TABLE RolAccount (
    rolId BIGINT NOT NULL ,
    accountId BIGINT NOT NULL ,
    CONSTRAINT pk_rolAccount
        PRIMARY KEY (rolId, accountId)
)ENGINE = InnoDB
;

CREATE TABLE Ordere (
	orderId BIGINT NOT NULL AUTO_INCREMENT ,
	distributed BIT NOT NULL ,
	version BIGINT NOT NULL ,
	CONSTRAINT pk_ordere
		PRIMARY KEY (orderId)
)ENGINE = InnoDB
;

CREATE TABLE ClientOrder (
	accountId BIGINT NOT NULL ,
	orderId BIGINT NOT NULL ,
	CONSTRAINT pk_clientOrdere
		PRIMARY KEY (accountId, orderId)
)ENGINE = InnoDB
;

CREATE TABLE Booking (
	bookingId BIGINT NOT NULL AUTO_INCREMENT ,
	accountId BIGINT NOT NULL ,
	day DATETIME NOT NULL ,
	people INT NOT NULL ,
	share BIT NOT NULL ,
	boardId BIGINT NOT NULL ,
	version BIGINT NOT NULL ,
	CONSTRAINT pk_booking
		PRIMARY KEY (bookingId)
)ENGINE = InnoDB
;

CREATE TABLE BookingClient (
	bookingId BIGINT NOT NULL ,
	accountId BIGINT NOT NULL ,
	CONSTRAINT pk_bookingClient
		PRIMARY KEY (bookingId, accountId)
)ENGINE = InnoDB
;

CREATE TABLE Board (
	boardId BIGINT NOT NULL AUTO_INCREMENT ,
	code VARCHAR(255) NOT NULL ,
	size INT NOT NULL ,
	version BIGINT NOT NULL ,
	CONSTRAINT pk_board
		PRIMARY KEY (boardId)
)ENGINE = InnoDB
;

CREATE TABLE BookingBoard (
	bookingId BIGINT NOT NULL ,
	boardId BIGINT NOT NULL ,
	CONSTRAINT pk_bookingBoard
		PRIMARY KEY (bookingId, boardId)
)ENGINE = InnoDB
;
