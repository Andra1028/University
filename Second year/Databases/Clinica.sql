create database Clinica
go
use Clinica 
go

create table Specializari(
id int primary key,
denumire varchar(50));


create table Diagnostice(
id int primary key,
denumire varchar(50),
descriere varchar(200));

create table Pacienti(
id int primary key,
nume varchar(30),
prenume varchar(30),
adresa varchar(50));

create table Medici(
id int primary key,
nume varchar(30),
prenume varchar(30),
specializare int foreign key references Specializari(id));


create table Consult(
medic int foreign key references Medici(id),
pacient int foreign key references Pacienti(id),
diagnostic int foreign key references Diagnostice(id),
data_consult date,
ora_consult time,
observatii varchar(100)
constraint pk_Consult primary key (medic, pacient)
);

insert into Specializari values
(1, 'Neurolog'),
(2, 'Denstist');

insert into Medici values
(1, 'N1', 'P1', 1),
(2, 'N2', 'P2', 1),
(3, 'N3', 'P3', 2),
(4, 'N4', 'P4', 2);

insert into Diagnostice values 
(1, 'Diagnostic1', 'Desc1'),
(2, 'Diagnostic2', 'Desc2');

insert into Pacienti values
(1, 'Nume1', 'Prenume1', '7782'),
(2, 'Nume2', 'Prenume2', '392'),
(3, 'Nume3', 'Prenume3', '26127'),
(4, 'Nume4', 'Prenume4', '91829');

insert into Consult values
(1, 1, 1, '2023-01-04','13:00:00', 'Observatii');
go

create or alter procedure insertConsult
@medic int,
@pacient int ,
@diagnostic int,
@observatii varchar(100),
@data date,
@ora time
as
begin
	declare @exista int
	select @exista=medic from Consult where medic=@medic and pacient=@pacient and data_consult=@data
	if (@exista >0)
	begin
		update Consult set diagnostic=@diagnostic , ora_consult=@ora, observatii=@observatii
		where medic=@medic and pacient=@pacient and data_consult=@data
	end
	else
	begin
		insert into Consult 
		values(@medic, @pacient, @diagnostic, @data, @ora, @observatii)
	end
end

exec insertConsult 2, 2, 2, 'Obs', '2022-12-29', '12:00:00';
exec insertConsult 2, 3, 2, 'Obs', '2022-12-29', '12:00:00';
exec insertConsult 3, 2, 2, 'Obs', '2023-01-04', '13:00:00';
exec insertConsult 3, 8, 2, 'Obs', '2023-01-04', '13:00:00';
exec insertConsult 3, 15, 2, 'Obs', '2023-01-04', '13:00:00';
select * from Consult
exec insertConsult 1, 1, 2, 'Obs', '2023-01-04', '16:00:00';

insert into Pacienti values
(5, 'Nume1', 'Prenume1', '7782'),
(6, 'Nume2', 'Prenume2', '392'),
(7, 'Nume3', 'Prenume3', '26127'),
(8, 'Nume4', 'Prenume4', '91829'),
(9, 'Nume4', 'Prenume4', '91829'),
(10, 'Nume4', 'Prenume4', '91829'),
(11, 'Nume4', 'Prenume4', '91829'),
(12,'Nume4', 'Prenume4', '91829'),
(13, 'Nume4', 'Prenume4', '91829'),
(14, 'Nume4', 'Prenume4', '91829'),
(15, 'Nume4', 'Prenume4', '91829'),
(16, 'Nume4', 'Prenume4', '91829'),
(17, 'Nume4', 'Prenume4', '91829'),
(18, 'Nume4', 'Prenume4', '91829'),
(19, 'Nume4', 'Prenume4', '91829'),
(20, 'Nume4', 'Prenume4', '91829'),
(21, 'Nume4', 'Prenume4', '91829');

insert into Consult values 
(1, 2, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 3, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 4, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 5, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 6, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 7, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 8, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 9, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 10, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 11, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 12, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 13, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 14, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 15, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 16, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 17, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 18, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 19, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 20, 1, '2023-01-04','13:00:00', 'Observatii'),
(1, 21, 1, '2023-01-04','13:00:00', 'Observatii');
go

create or alter view lista
as
select m.nume, m.prenume from Medici m inner join Consult c on m.id=c.medic
group by c.medic, m.nume, m.prenume
having count(*)>20
go

select * from lista

create function MediciConsult(@data date, @ora time)
returns table 
as
return select m.nume, m.prenume from Medici m inner join Consult c on c.medic=m.id
where @data=data_consult and @ora=ora_consult
group by c.medic, m.nume, m.prenume
having count(*)>1

select * from dbo.MediciConsult('2023-01-04','13:00:00') 