CREATE DATABASE MersulTrenurilor
GO
USE MersulTrenurilor
GO
CREATE TABLE Tipuri
(
    Id_Tip INT PRIMARY KEY IDENTITY,
    Descriere VARCHAR(50)
)
CREATE TABLE Trenuri
(
    Id_Tren INT PRIMARY KEY IDENTITY,
    Nume VARCHAR(50),
    Id_Tip INT FOREIGN KEY REFERENCES Tipuri(Id_Tip)
)
CREATE TABLE Rute
(
    Id_Ruta INT PRIMARY KEY IDENTITY,
    Nume VARCHAR(50),
    Id_Tren INT FOREIGN KEY REFERENCES Trenuri(Id_Tren)
)
CREATE TABLE Statii
(
    Id_Statie INT PRIMARY KEY IDENTITY,
    Nume VARCHAR(50)
)
CREATE TABLE RuteStatii
(
    Id_Ruta INT FOREIGN KEY REFERENCES Rute(Id_Ruta),
    Id_Statie INT FOREIGN KEY REFERENCES Statii(Id_Statie),
    Ora_Sosirii TIME,
    Ora_Plecarii TIME,
    CONSTRAINT pk_RuteStatii PRIMARY KEY(Id_Ruta, Id_Statie)
)


insert into Tipuri(descriere) values
('de marfa'),
('personal'),
('cfr'),
('tfc'),
('inter regio')
 
select * from Tipuri
 
insert into Trenuri(nume, Id_Tip) values
('Thomas', 1),
('Edison', 2),
('George', 3),
('Funnyrau', 2),
('Bratucu', 1),
('Cataciuciu', 1),
('Emicici', 2),
('Ioanana', 3),
('Mamarius', 2),
('Jan', 1)
 
select * from Trenuri
 
insert into Rute(nume, Id_Tren) values
('cluj-arad', 10),
('cluj-disneyland', 4),
('craiova-cracovia', 9),
('paris-micul-paris', 8),
('arad-brasov', 10),
('toma-city', 1),
('cluj-oradea', 8)
 
select * from Rute
 
insert into Statii(nume) values
('alesd'),
('oradea'),
('razboieni'),
('bratca'),
('campia turzii'),
('ohaba de sub piatra'),
('bucuresti nord'),
('cluj nord'),
('suncuius'),
('vaslui')
 
select * from Rute
select * from Statii
 
insert into RuteStatii(Id_Ruta, Id_Statie, Ora_Sosirii, Ora_Plecarii) values
(1, 1, '15:13', '15:14'),
(2, 2, '12:13', '12:30'),
(3, 3, '11:13', '11:20'),
(3, 4, '11:40', '11:50'),
(4, 4, '15:13', '15:20'),
(1, 5, '10:13', '10:15'),
(2, 3, '13:13', '13:15')
go

insert into RuteStatii values
(7,4,'11:40', '11:50');

create or alter procedure upsert_station
@numeRuta varchar(50),
@numeStatie varchar(50),
@oraSosire time,
@oraPlecare time
as
begin
    declare @idRuta int=0
    declare @idStatie int=0
 
    set @idRuta = (select top 1 Id_Ruta from Rute where Nume = @numeRuta)
    print @idRuta
 
    set @idStatie = (select top 1 Id_Statie from Statii where Nume = @numeStatie)
    print @idStatie
 
    if (exists (select * from RuteStatii 
            where Id_Ruta = @idRuta and Id_Statie = @idStatie))
        update RuteStatii set Ora_Sosirii=@oraSosire, Ora_Plecarii=@oraPlecare
        where Id_Ruta = @idRuta and Id_Statie = @idStatie
    else
        insert into RuteStatii(Id_Ruta, Id_Statie, Ora_Sosirii, Ora_Plecarii) values
        (@idRuta, @idStatie, @oraSosire, @oraPlecare)
end
go
 
select * from Rute
select * from Statii
select * from RuteStatii
 
exec upsert_station 'cluj-arad', 'oradea', '10:30', '10:34'
 
select * from Rute
select * from Statii
select * from RuteStatii

create or alter view ruteCuToateStatiile
as 
select Rute.Nume
from RuteStatii inner join Rute on RuteStatii.Id_Ruta = Rute.Id_Ruta
group by Rute.Id_Ruta, Rute.Nume
having count(RuteStatii.Id_Statie) = (Select count(Statii.Id_Statie) from Statii)
go
 
select * from ruteCuToateStatiile

create or alter function listaStatii(@ora time)
returns table
as
return select s.nume from Statii s inner join RuteStatii rs on rs.Id_Statie=s.Id_Statie
inner join Rute r on rs.Id_Ruta=r.Id_Ruta
where rs.Ora_Sosirii=@ora
group by s.nume
having count(r.id_Tren)>1 

select * from dbo.listaStatii('11:40')