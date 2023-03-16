use restaurant
go
Insert into Bucatari(CNP_bucatar, nume_bucatar, salariu) 
values('69213020015', 'Catalin Scarlatescu', 5000);

Insert into Bucatari(CNP_bucatar, nume_bucatar, salariu) 
values('58912100602', 'Florin Dumitrescu', 5300),
('50010180277', 'Sorin Bontea', 4900);

insert into Tipuri_Produse(tip)
values('Mic dejun')
insert into Tipuri_Produse(tip)values
('Salate'),
('Supe & Ciorbe'),
('Fel Principal'),
('Paste'),
('Pizza'),
('Garnituri'),
('Bautura');

select * from Tipuri_Produse;

insert into Masini(nr_masina,model,consum,an) values
('CJ01VIP', 'Audi A4', 9, 2015),
('CJ02VIP', 'Audi A4', 9, 2013);


insert into Ingrediente(nume_ingrediente) values
('ciuperci');

insert into Ingrediente(nume_ingrediente) values
('smantana de gatit'),
('parmezan'),
('brie'),
('gorgonzola'),
('mozzarella'),
('ou'),
('paine'),
('ceapa'),
('sos de rosii'),
('rosii'),
('porumb'),
('ardei'),
('pui'),
('ton'),
('salata'),
('masline');

select * from Ingrediente;

insert into Clienti(CNP_client,nume_client,adresa_client,telefon_client) values
('8439', 'Petrica', 'Strada fagului 7', '0711111111'),
('8440', 'Petronela', 'Strada ariesului 23', '0711111112'),
('8441', 'Marcel', 'Strada de mijloc 8', '0711111113'),
('8442', 'Viorica', 'Strada memorandumului 9', '0711111114'),
('8443', 'Vasile', 'Strada viitorului 112', '0711111115'),
('8444', 'Cainescu', 'Strada Tzanca 1', '0711111116'),
('8445', 'Maria', 'Strada Uraganul 1', '0711111117'),
('8446', 'Marinela', 'Strada universitatii 99', '0711111118'),
('8448', 'Dorina', 'Strada muncii 6', '0711111119'),
('8449', 'Costel', 'Strada fagetului 10', '0711111120'),
('8450', 'Biju', 'Strada sarachiei 3', '0711111121');

select * from Clienti;

insert into Livratori(CNP_livrator,nume_livrator,masina,salariu) values
('747343747', 'Roberto','CJ01VIP', 3000),
('884988389' ,'Malone', 'CJ02VIP', 3000),
('893948390', 'Gina', 'CJ01VIP', 3500),
('626537263', 'Jony', 'CJ02VIP', 3000);

select * from Livratori;

select * from Tipuri_Produse;

insert into Produse(nume_produs,pret_produs,tip_produs) values
('Ciorba radauteana', 10, 3),
('Omleta', 15, 1),
('English breakfast' ,17, 1),
('Salata caesar', 20, 2),
('Salata cu ton', 21, 2),
('Salata cu creveti', 25, 2),
('Ciorba de legume', 10, 3),
('Supa de pui cu taitei', 10, 3),
('Pui cu sos de ciuperci', 25, 4),
('Aripioare de pui picante', 22, 4),
('Quesadilla', 23, 4),
('Cascaval pane', 21, 4),
('Muschiulet de porc', 25, 4),
('Tocanita', 22, 4),
('Orez cu pui', 23, 4),
('Fasole cu carnaciori', 23, 4),
('Pastrav la gratar', 28, 4),
('Salau in fulgi de porumb', 30, 4),
('Paste quattro formaggi', 25, 5),
('Paste Carbonara', 23, 5),
('Pastele casei', 26, 5),
('Pizza quattro stagioni', 26, 6),
('Pizza quattro formaggi', 28, 6),
('Pizza Diavola', 25, 6),
('Cartofi prajiti', 6, 7),
('Pireu', 5, 7),
('Legume', 7, 7),
('Cartofi copti', 6,7),
('Apa plata', 4, 8),
('Apa minerala', 4, 8),
('Coca Cola', 5, 8),
('Cafea', 6,8),
('Bere', 5, 8),
('Fanta', 5, 8);

select * from Produse;

insert into Ingrediente_Produse(ID_ingrediente,ID_produs) values
(1,9),
(1,7),
(1,21),
(1,22),
(1,27),
(2,9),
(2,19),
(2,20),
(3,4),
(3,9),
(3,19),
(3,20),
(3,21),
(3,23),
(4,19),
(4,23),
(4,11),
(5,19),
(5,23),
(6,19),
(6,23),
(7,1),
(7,2),
(7,3),
(7,20),
(8,3),
(8,5),
(8,4),
(8,6),
(9,11),
(9,21),
(10,11),
(10,21),
(10,22),
(11,3),
(11,4),
(11,5),
(11,6),
(11,11),
(11,14),
(11,27),
(12,4),
(12,5),
(12,6),
(12,11),
(12,27),
(13,1),
(13,4),
(13,5),
(13,6),
(13,7),
(13,11),
(13,14),
(13,27),
(14,1),
(14,8),
(14,9),
(14,10),
(14,15),
(15,5),
(16,4),
(16,5),
(16,6),
(17,4),
(17,5),
(17,6);

use Restaurant;
go

select * from Produse;

select * from Clienti;

select * from Ingrediente_Produse;
insert into Comenzi(ID_produs,ID_client,ID_bucatar,cost) values
(13,8439,'69213020015', 30);
insert into Comenzi(ID_produs,ID_client,ID_bucatar,cost) values
(23, 8445,'58912100602',33);
insert into Comenzi(ID_produs,ID_client,ID_bucatar,cost) values
(16, 8444, '50010180277', 28);
insert into Comenzi(ID_produs,ID_client,ID_bucatar,cost) values
(20, 8450, '69213020015', 28);
insert into Comenzi(ID_produs,ID_client,ID_bucatar,cost) values
(18, 8440, '58912100602', 35);
insert into Comenzi(ID_produs,ID_client,ID_bucatar,cost) values
(3, 8441, '50010180277', 22);
insert into Comenzi(ID_produs,ID_client,ID_bucatar,cost) values
(8, 8448, '50010180277', 15);

select * from Comenzi;

insert into Livrari(ID_comanda, ID_livrator, zi, ora) values
(8, '747343747', '2022-09-21', '11:10:00');
insert into Livrari(ID_comanda, ID_livrator, zi, ora) values
(21, '747343747', '2022-09-21', '12:20:00'),
(22, '747343747', '2022-09-21', '12:30:00'),
(23, '747343747', '2022-09-21', '13:40:00'),
(24, '747343747', '2022-09-21', '14:30:00'),
(25, '884988389', '2022-09-22', '12:50:00'),
(27, '884988389', '2022-09-22', '14:20:00'),
(28, '884988389', '2022-09-22', '15:00:00'),
(29, '893948390', '2022-09-23', '11:10:00'),
(30, '893948390', '2022-09-23', '16:40:00'),
(31, '893948390', '2022-09-23', '18:50:00'),
(32, '626537263', '2022-09-24', '10:40:00'),
(33, '626537263', '2022-09-24', '17:00:00'),
(34, '626537263', '2022-09-24', '20:30:00');

select * from Livrari;



/* 1. bucatarul si media costului comenzilor pregatite de el*/
select  AVG(cost) as [Cost mediu], nume_bucatar
from Comenzi, Bucatari 
where CNP_bucatar=ID_bucatar
group by nume_bucatar, cost
having AVG(cost)>15
order by cost;

/*2. Afiseaza comanda, clientul, bucatarul si costul comenzii ordonat dupa cost*/
select Clienti.nume_client, Bucatari.nume_bucatar,Comenzi.ID_comanda, Comenzi.cost 
from Clienti,Bucatari, Comenzi 
where ID_client=CNP_client and CNP_bucatar=ID_bucatar
Order by cost;

/*3. Afiseaza comanda, clientul, bucatarul si costul nou al comenzii ( cost vechi -3)*/
select Clienti.nume_client, Bucatari.nume_bucatar,Comenzi.ID_comanda , Comenzi.cost-3
as cost_nou
from Clienti,Bucatari, Comenzi 
where ID_client=CNP_client and CNP_bucatar=ID_bucatar ;


/*4. Afiseaz clientul si costul comenzilor acestuiadaca este mai mic decat 70, grupate de cost*/
select   Clienti.nume_client as [Client], SUM(Comenzi.cost) as [Cost]
from Clienti,Comenzi 
where Clienti.CNP_client=Comenzi.ID_client
group by nume_client,cost
having SUM(cost)<70
order by cost;


/* 5. Insereaza in TabelInserare ziua livrarii, masina cu care s-a livrat comanda si livratorul*/
select distinct Livrari.zi,Masini.nr_masina, Livratori.nume_livrator
into dbo.TabelInserare
from livrari,livratori,Masini
where  ID_livrator=CNP_livrator and masina=nr_masina ;

select * from TabelInserare;
Drop table TabelInserare;

/* 6. Selecteaza din tabele ziua livrarii, masina cu care s-a livrat comanda si livratorul*/
SELECT DISTINCT Livrari.zi,Livratori.nume_livrator,Masini.nr_masina
FROM Livrari 
INNER JOIN 
(Livratori INNER JOIN Masini 
ON masina=nr_masina) 
ON ID_livrator=CNP_livrator;


/* 7. Selecteaza toti clientii, comanda acestora si bucatarul care a facut comanda*/
select distinct co.cost, c.nume_client, b.nume_bucatar
from Clienti c
left outer join comenzi co  on c.CNP_client=co.ID_client 
left outer join Bucatari b on b.CNP_bucatar=co.ID_bucatar; 

/*select DISTINCT l.nume_livrator, liv.zi, m.nr_masina
from  Livratori l
right outer join Livrari liv  on l.CNP_livrator=liv.ID_livrator 
right outer join Masini m on m.nr_masina=l.masina;*/

/*select distinct co.cost, c.nume_client, b.nume_bucatar
from Clienti c
full outer join comenzi co  on c.CNP_client=co.ID_client 
full outer join Bucatari b on b.CNP_bucatar=co.ID_bucatar; */

/* 8. selecteaza toate produsele si ingredientele acestora*/
select * from 
Produse p inner join Ingrediente_Produse i_p on i_p.ID_produs=p.produs_id 
inner join Ingrediente i on i_p.ID_ingrediente=i.ID_ingrediente ;


/*9. selecteaza toate ingredientele si rpodusele in care se gasesc grupate dupa ingrediente*/
select  Produse.nume_produs as Produse,Ingrediente.ID_ingrediente 
from Produse, Ingrediente_Produse ,Ingrediente 
where Ingrediente.ID_ingrediente=Ingrediente_Produse.ID_ingrediente 
and Ingrediente_Produse.ID_produs=Produse.produs_id
group by Ingrediente.ID_ingrediente, Produse.nume_produs;


/*10. selecteaza toate produsele si ingredientele fiecaruia grupate dupa produse*/
select  Produse.nume_produs as Produse,Ingrediente.nume_ingrediente 
from Produse, Ingrediente_Produse ,Ingrediente 
where Ingrediente.ID_ingrediente=Ingrediente_Produse.ID_ingrediente 
and Ingrediente_Produse.ID_produs=Produse.produs_id
group by  Produse.nume_produs, Ingrediente.nume_ingrediente;





