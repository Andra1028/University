create database ExamenPractic2
go
use ExamenPractic2
go

create table Orase(
id int primary key,
regiune varchar(50),
tara varchar(50));

create table Tipuri(
id int primary key,
denumire varchar(50),
descriere varchar(50));

create table Participanti(
id int primary key,
nume varchar(50),
prenume varchar(50),
gen varchar(20),
data_nasterii date);

create table Competitii(
id int primary key,
denumire varchar(50),
data_incepere date,
data_finalizare date,
tip int foreign key references Tipuri(id),
oras int foreign key references Orase(id));

create table ParticipantiCompetitii(
participant int foreign key references Participanti(id),
competitie int foreign key references Competitii(id),
taxa float,
locul int,
constraint pk_ParticipantiCompetitii primary key (participant, competitie));

insert into Orase values
(1, 'Regiune 1' , 'Tara 1'),
(2, 'Regiune 2', 'Tara 2');

insert into Tipuri values
(1, 'Tip 1', 'Descriere 1'),
(2, 'Tip 2', 'Descriere 2');

insert into Participanti values
(1, 'Nume 1', 'Prenume 1', 'feminin', '2000-09-15'),
(2, 'Nume 2', 'Prenume 2', 'feminin', '1997-12-08'),
(3, 'Nume 3', 'Prenume 3', 'masculin', '2001-01-01'),
(4, 'Nume 4', 'Prenume 4', 'feminin', '2004-07-27'),
(5, 'Nume 5', 'Prenume 5', 'masculin', '1999-11-04');

insert into Competitii values
(1, 'Competitie 1', '2022-09-18', '2022-09-21',1,1),
(2, 'Competitie 2', '2021-10-10', '2021-10-11',1,2),
(3, 'Competitie 3', '2020-02-06', '2020-02-10',2,1),
(4, 'Competitie 4', '2023-01-02', '2023-01-03',2,2),
(5, 'Competitie 5', '2018-06-22', '2018-06-24',2,1);

insert into ParticipantiCompetitii values
(1,1,240,3),
(1,3,400,2),
(2,4,100,1),
(5,4,200,4),
(4,2,100,2),
(5,1,300,5),
(3,5,600,1);


create or alter procedure insertParticipantiCompetitii
@participant int,
@competitie int,
@taxa float,
@loc int
as
begin
	declare @exista int
	select @exista=participant from ParticipantiCompetitii 
	where participant=@participant and competitie=@competitie
	if(@exista > 0)
	begin
		update ParticipantiCompetitii set taxa=@taxa, locul=@loc 
		where participant=@participant and competitie=@competitie
	end
	else
	begin
		insert into ParticipantiCompetitii values (@participant, @competitie, @taxa, @loc)
	end
end

exec insertParticipantiCompetitii 1, 5, 250, 3
exec insertParticipantiCompetitii 1, 1, 300, 1


select * from ParticipantiCompetitii

create or alter view listaCompetitii
as
	select s.denumire from(select c.denumire, count(p.nume) as cati from Competitii c inner join ParticipantiCompetitii pc 
	on c.id=pc.competitie inner join Participanti p on pc.participant=p.id
	group by c.denumire) as s
	where s.cati= (select max(m.cati) from (select c.denumire, count(p.nume) as cati from Competitii c inner join ParticipantiCompetitii pc 
	on c.id=pc.competitie inner join Participanti p on pc.participant=p.id
	group by c.denumire) as m)


select * from listaCompetitii

exec insertParticipantiCompetitii 1, 4, 300, 1





