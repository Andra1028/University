create database Biblioteca
go
use Biblioteca
go



create table Autori(
id int primary key,
nume varchar(30),
prenume varchar(30));

create table Librarii(
id int primary key,
nume varchar(40),
adresa varchar(100));

create table Domenii(
id int primary key,
descriere varchar(50));


create table Carti(
id int primary key,
titlu varchar(50),
domeniu int foreign key references Domenii(id),
librarie int foreign key references Librarii(id),
data_cumparare date);

create table CartiAutori(
carte int foreign key references Carti(id),
autor int foreign key references Autori(id),
constraint pk_CartiAutori primary key(carte, autor));

insert into Domenii values
(1,'stiinta'),
(2, 'literartura');

insert into Librarii values
(1, 'Libris', '63799'),
(2, 'Carturesti', '38938');

insert into Autori values
(1, 'Creanga', 'Ion'),
(2, 'Fitzgerald', 'F. Scott');

insert into Carti values
(1, 'Marele Gatsby', 2,2, '2022-12-19'),
(2, 'Amintiri din copilarie', 2, 1, '2022-12-20'),
(3, 'La rascruce de vanturi', 2, 1, '2022-12-10');
go

create or alter procedure insertCartiAutori
@nume varchar(30),
@prenume varchar(30),
@carte varchar(50)
as
begin
	declare @idA int
	declare @idC  int
	declare @exista int
	select @idA=id from Autori where nume=@nume and prenume=@prenume
	if (@idA >0)
	begin
		select @idC=id from Carti where titlu=@carte
		select @exista=carte from CartiAutori where carte=@idC and autor=@idA
		if (@exista >0)
		begin
			print 'Exista deja'
		end
		else
		begin
		insert into CartiAutori values (@idC, @idA)
		end
	end
	else
	begin
		declare @autor int
		select top 1 @autor=id from Autori Order by id desc
		set @autor=@autor+1
		insert into Autori values(@autor, @nume, @prenume)
		select @idC=id from Carti where titlu=@carte
		insert into CartiAutori values (@idC, @autor)
	end
end

exec insertCartiAutori 'Creanga', 'Ion', 'Amintiri din copilarie'
exec insertCartiAutori 'Bronte', 'Emily', 'La rascruce de vanturi'

select * from Autori
select * from CartiAutori

insert into Carti values
(4, 'Harry Potter si piatra filizofala', 2,1, '2008-12-19'),
(5, 'Harry Potter si camera secretelor', 2, 1, '2022-12-20'),
(6, 'Harry Potter si prizonierul din Azkaban', 2, 1, '2022-12-10'),
(7, 'Harry Potter si pocalul de foc', 2,1, '2022-12-19');
go

create or alter view cateCarti
as
	select l.nume , sum(c.librarie) as nrCarti
	from Librarii l inner join Carti c on c.librarie=l.id
	where c.data_cumparare>'2010-12-31' 
	group by c.librarie,l.nume
	having sum(c.librarie)>4

select * from cateCarti

insert into CartiAutori values (1,2)

select * from CartiAutori

select carte, count(*) as nrAutori from CartiAutori group by carte

create function listaCarti (@nrAutori int)
returns table
as
	return select l.nume, l.adresa, c.titlu, count(ca.autor) as nrAutori
	from Librarii l inner join Carti c on l.id=c.librarie
	inner join CartiAutori ca on c.id=ca.carte
	group by ca.carte, c.titlu,l.nume,l.adresa
	having count(ca.autor)=@nrAutori


select * from dbo.listaCarti(1)