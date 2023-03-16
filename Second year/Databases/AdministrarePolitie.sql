create database AdministrarePolitie
go
use AdministrarePolitie
go

Create table Sectii
(id_sectie int primary key,
nume varchar(200) not null,
adresa varchar(200) not null
);

Create table Grade
(id_grad int primary key,
nume varchar(200) not null,
);
Create table Sector
(id_sector int primary key,
nume varchar(200) not null,
);

create table Politisti
(id_politist int primary key,
nume varchar(40),
prenume varchar(40),
sectie int foreign key references Sectii(id_sectie),
grad int foreign key references Grade(id_grad)
);

create table Programari
(
sector int foreign key references Sector(id_sector),
politist int foreign key references Politisti(id_politist),
constraint pk_programari primary key(sector, politist),
data_intrare date,
ora_intrare time,
data_iesire date,
ora_iesire time
);

insert into Sectii values
(1, 'Sectia 1', '123'),
(2, 'Sectia 2', '124'),
(3, 'Sectia 3', '125');

insert into Grade values
(1, 'Grad 1'),
(2, 'Grad 2');

insert into Sector values
(1, 'Sector 1'),
(2, 'Sector 2'),
(3, 'Sector 3');

insert into Politisti values
(1,'Nume1', 'Prenume1', 1,1),
(2,'Nume2', 'Prenume2', 3, 2),
(3,'Nume3', 'Prenume3', 2,1),
(4, 'Nume4', 'Prenume4',1,2);

insert into Programari values
(1, 1, '2022-12-21', '21:21:21', '2022-12-22', '03:15:24');
go

create or alter procedure insertProgramari
@politist int,
@sector int,
@data_intare date,
@ora_intrare time,
@data_iesire date,
@ora_iesire time
as 
begin
	declare @selected int
	select @selected= politist from Programari 
	where politist=@politist and sector=@sector and data_intrare=@data_intare;
	if (@selected >0)
	begin
		update Programari set ora_intrare=@ora_intrare , data_iesire=@data_iesire, ora_iesire=@ora_iesire
		where politist=@politist and sector=@sector and data_intrare=@data_intare;
	end
	else
	begin
		insert into Programari values(@sector,@politist,@data_intare,@ora_intrare,@data_iesire,@ora_iesire);
	end
end
go

exec insertProgramari 1, 2 , '2022-10-29','13:40:13','2022-10-29' , '20:20:20'
exec insertProgramari 1, 1 , '2022-12-21','13:40:13','2022-12-22' , '20:20:20'
select * from Programari

insert into Programari values
(1,3,'2022-12-13','08:00:00','2022-12-13','12:00:00');

insert into Programari values
(2,3,'2022-12-13','08:00:00','2022-12-13','12:00:00');

insert into Programari values
(2,4,'2022-12-13','08:00:00','2022-12-13','12:00:00');
insert into Programari values
(1,4,'2022-12-13','08:00:00','2022-12-13','12:00:00'),
(3,4,'2022-12-13','08:00:00','2022-12-13','12:00:00');

select * from Programari


select politist,sum(datediff(hour,ora_intrare,ora_iesire)) as ore  from Programari 
where data_intrare>'2022-11-30' and data_intrare<'2023-01-01' group by politist, ora_intrare,ora_iesire


create or alter view lista
as
select p.sectie, p.nume , p.prenume, sum(datediff(hour,ora_intrare,ora_iesire)) as [ore]  
from Politisti p inner join Programari pr on p.id_politist=pr.politist 
where data_intrare>'2022-11-30' and data_intrare<'2023-01-01' 
group by p.nume,p.prenume, ora_intrare,ora_iesire,p.sectie


select * from lista


create function tabelLista (@data_intrare date, @ora_intrare time)
returns table 
as
return select nume, prenume from Politisti p inner join
(select politist from Programari where @data_intrare=data_intrare and @ora_intrare=ora_intrare 
group by politist 
having count(*)>1) pr on p.id_politist=pr.politist Group by p.nume, p.prenume;

select * from dbo.tabelLista('2022-12-13','08:00:00')