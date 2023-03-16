create database GestiuneVanzari
go
use GestiuneVanzari
go

create table Clienti(
id int primary key,
denumire varchar(50),
cod_fiscat varchar(20));

create table Produse(
id int primary key,
denumire varchar(50),
unitate_masura varchar(20));

create table Agenti(
id int primary key,
nume varchar(30),
prenume varchar(30));

create table Facturi(
id int primary key,
numar int,
data_emitere date,
client int foreign key references Clienti(id),
Agent int foreign key references Agenti(id));

create table Vanzari(
factura int foreign key references Facturi(id),
produs int foreign key references Produse(id),
nr_ordine int,
pret float,
cantitate int
constraint pk_Vanzari primary key (factura, produs));

insert into Clienti values
(1, 'Den1', '8282'),
(2,'Den2', '7848'),
(3, 'Den3', '7888'),
(4, 'Den4', '1452');

insert into Produse values
(1, 'Produs1', 'litru'),
(2, 'Produs2', 'kilogram'),
(3, 'Produs3', 'metru'),
(4, 'Produs4', 'kilogram'),
(5, 'Produs5', 'litru');

insert into Produse values
(6,'Shaorma' ,'kg');

insert into Agenti values
(1, 'Nume1', 'Prenume1'),
(2, 'Nume2', 'Prenume2'),
(3,'Nume3', 'Prenume3');

insert into Facturi values
(1,34,'2010-12-10',1,1),
(2,35,'2010-11-10',3,1),
(3,36,'2010-11-10',1,2),
(4,37,'2010-11-10',2,1),
(6,38,'2010-12-10',4,3);

insert into Vanzari values
(1,6, 123, 310,10);


create or alter procedure insertVanzari
@factura int,
@produs int, 
@nr_ordine int,
@pret float,
@cantitate int
as
begin
	declare @exista int
	select @exista=factura from Vanzari where factura=@factura and produs=@produs
	if(@exista >0)
	begin
		update Vanzari set cantitate=-1*cantitate where factura=@factura and produs=@produs
	end
	else
	begin
		insert into Vanzari values (@factura, @produs, @nr_ordine, @pret, @cantitate)
	end
end

exec insertVanzari 2, 3, 45, 20, 7
select * from Vanzari
exec insertVanzari 2, 3, 45, 20, 7
exec insertVanzari 1, 3 ,5 ,12,7

create or alter view listaShaorma
as
select c.denumire, f.id, f.data_emitere, sum(v.pret) as PretTotal 
from Clienti c inner join Facturi f on c.id=f.client
inner join (select v.factura from Vanzari v inner join 
Produse p on p.id=v.produs
group by v.factura, p.denumire
having p.denumire='Shaorma') s on s.factura=f.id
inner join Vanzari v on v.factura=s.factura
group by v.factura, f.id, f.data_emitere, c.denumire
having sum(v.pret)>300 

select * from listaShaorma

create function listaFacturi(@an int)
returns table
as
return select datepart(month, f.data_emitere) as Luna, sum(v.pret) as PretTotal ,a.nume,a.prenume
from Facturi f inner join Vanzari v on f.id=v.factura
inner join Agenti a on a.id=f.agent
group by datepart(month, f.data_emitere), f.data_emitere,a.nume,a.prenume
having datepart(year, f.data_emitere)=@an
 

drop function listaFacturi
select * from Facturi
select * from Vanzari
select * from dbo.listaFacturi(2010)

