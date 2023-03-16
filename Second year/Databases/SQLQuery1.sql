CREATE DATABASE Restaurant;
GO
USE Restaurant;
GO

CREATE TABLE Ingrediente
(ID_ingrediente INT PRIMARY KEY IDENTITY,
nume_ingrediente VARCHAR(200) NOT NULL);

CREATE TABLE Produse
(ID_produs INT PRIMARY KEY IDENTITY,
nume_produs VARCHAR(200) NOT NULL,
pret_produs FLOAT);

CREATE TABLE Ingrediente_Produse
(ID_produs  INT FOREIGN KEY REFERENCES Produse(ID_produs),
ID_ingrediente INT FOREIGN KEY REFERENCES Ingrediente(ID_ingrediente),
CONSTRAINT pk_Ingredienete_Produse PRIMARY KEY(ID_produs, ID_ingrediente));


CREATE TABLE Clienti
(CNP_client INT PRIMARY KEY,
nume_client VARCHAR(50),
adresa_client VARCHAR(200),
telefon_client VARCHAR(13));


create table Masini
(nr_masina varchar(10) primary key,
model varchar(50),
an int,
consum float);


Create table Livratori
(CNP_livrator varchar(12) PRIMARY key,
nume_livrator varchar(50),
salariu float,
masina varchar(10) foreign key references Masini(nr_masina));



Create table Bucatari
(CNP_bucatar varchar(12) primary key,
nume_bucatar varchar(50),
salariu float);


CREATE TABLE Comenzi
(ID_comanda int primary key identity,
ID_produs INT FOREIGN KEY REFERENCES Produse(ID_produs),
ID_client INT FOREIGN KEY REFERENCES Clienti(CNP_client),
ID_bucatar varchar(12) foreign key references Bucatari(CNP_bucatar),
cost float);

create table Livrari
(ID_comanda int foreign key references Comenzi(ID_comanda),
ID_livrator varchar(12) foreign key references Livratori(CNP_livrator),
zi date,
ora time,
constraint pk_Livrari primary key (ID_comanda, ID_livrator));

create table Tipuri_Produse
(ID_tip int primary key identity,
tip varchar(100) not null);

alter table Produse
add tip_produs int foreign key references Tipuri_Produse(ID_tip);