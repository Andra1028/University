create database  Spital
go
use Spital
go

create table Departamente(
id int primary key,
nume varchar(50),
non_stop bit);


create table Doctori(
id int primary key,
nume varchar(50),
data_nasterii date);


create table Pacienti(
id int primary key,
nume varchar(50));

create table Boli(
id int primary key,
denumire varchar(50));

create table Tratamente(
id int primary key,
descriere varchar(50));

create table DepartamenteDoctori(
departament int foreign key references Departamente(id),
doctor int foreign key references Doctori(id),
constraint pk_DepDoc primary key (departament, doctor));

create table DoctoriPacienti(
doctor int foreign key references Doctori(id),
pacient int foreign key references Pacienti(id),
constraint pk_DocP primary key (doctor, pacient));

create table PacientiBoli(
pacient int foreign key references Pacienti(id),
boala int foreign key references Boli(id),
constraint pk_PB primary key (pacient, boala));

create table BoliTratamente(
boala int foreign key references Boli(id),
tratament int foreign key references Tratamente(id),
constraint pk_BT primary key (boala, tratament));

insert into Departamente values
(1, 'pediatrie', 'TRUE'),
(2, 'pediatrie ceva', 'FALSE'),
(3, 'departament 3', 'TRUE');

select * from Departamente where nume like '%pediatrie%'

insert into Doctori values
(1,'Doc1', '1970-12-04'),
(2,'Doc2', '1980-03-18'),
(3,'Doc3', '1984-08-23');

insert into Doctori values
(4, 'Doc4', '1992-05-28');

insert into Pacienti values
(1, 'Pacient1'),
(2, 'Pacient2'),
(3, 'Pacient3'),
(4, 'Pacient4'),
(5, 'Pacient5');

insert into Boli values
(1, 'Boala1'),
(2,'Boala2');

insert into Tratamente values
(1, 'Tratament 1'),
(2, 'Tratament 2'),
(3, 'Tratament 3');

insert into DepartamenteDoctori values
(1,1),
(2,1),
(2,3),
(3,2);

insert into DepartamenteDoctori values
(1,2),
(1,3),
(1,4);

insert into DoctoriPacienti values
(1,2),
(1,4),
(2,1),
(2,2),
(2,3),
(3,2),
(3,5);

insert into PacientiBoli values
(1,1),
(2,1),
(3,1),
(4,1),
(5,2);

insert into Boli values (3, 'Boala3');
insert into BoliTratamente values
(1,1),
(1,2),
(2,1),
(3,1),
(1,3);

insert into PacientiBoli values
(1,3),
(2,3),
(3,3),
(5,3);

create or alter function CateBoli()
returns int
as
begin
	declare @cate int
	set @cate =0
	select @cate=count(*) from (select b.denumire from Boli b inner join PacientiBoli pb on b.id=pb.boala
	group by pb.boala, b.denumire
	having count(*)>3) as Boli
	return @cate
end

print dbo.CateBoli()

update Tratamente set descriere= 'A Tratament 1' where id=1
update Tratamente set descriere= 'A Tratament 2' where id =2

insert into BoliTratamente values (2,3), (3,3);

create or alter view listaTratamente
as
select t.descriere from Tratamente t inner join BoliTratamente bt on t.id=bt.tratament
where t.descriere like 'A%'
group by t.id, t.descriere
having count(*)>2

select * from listaTratamente

create or alter view listaDepartamente
as
select d.nume from Departamente d inner join DepartamenteDoctori dd on d.id=dd.departament
where d.non_stop='TRUE'
group by dd.departament, d.nume
having count(*)>3

select * from listaDepartamente
