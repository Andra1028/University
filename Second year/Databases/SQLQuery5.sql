use Restaurant
go

-------------------Check functions to see if the CRUD operations are tested correctly
				   -- returns 0 on succes
				   -- returns 1 and error message on failure



create or alter procedure CheckCRUD
	@noRows int,
	@flag bit output,
	@error varchar(32) output
as
begin
	set @flag = 0
	if @noRows = 0
		begin
			set @flag = 1
			set @error = 'Row count must be > 0.'
		end
end
go



-------------------CRUD on table 'ingrediente'
create or alter procedure CRUDingrediente
	@noRows int
as
begin
	set nocount on
	declare @flag bit
	declare @error varchar(32)

	exec CheckCRUD @noRows, @flag output, @error output
	
	if @flag = 1
		begin
			print 'Error: ' + @error
		end

	else
		begin
			declare @i int
			set @i = 1

			declare @testId int
			select top 1 @testId = id_ingrediente from Ingrediente order by id_ingrediente desc
			declare @idCopy int
			set @idCopy = @testId
			
			--Insert
			while @i <= @noRows
				begin
					set @testId = @testId + 1
					
					insert into Ingrediente values (@testId,'test Ingrediente')

					set @i = @i + 1
				end


			--Select
			select * from Ingrediente

			--Update
			update Ingrediente set nume_ingrediente='test Ingrediente Updated' where ID_ingrediente > @idCopy
			select * from Ingrediente
			--Delete
			delete Ingrediente where ID_ingrediente > @idCopy
			

		end

end
go


insert into Ingrediente values (1, 'ceapa');




--------------------CRUD on table 'produse'
create or alter procedure CRUDproduse
	@noRows int
as
begin
	set nocount on
	declare @flag bit
	declare @error varchar(32)

	exec CheckCRUD @noRows, @flag output, @error output

	if @flag = 1
		begin
			print 'Error: ' + @error
		end
	else
		begin
			declare @tipId int
			select top 1 @tipId =ID_tip  from Tipuri_Produse order by ID_tip desc
			set @tipId = @tipId + 1

			declare @prodId int
			select top 1 @prodId = produs_id from Produse order by produs_id desc
			declare @idCopy int = @prodId
			set @prodId = @prodId + 1
			

			declare @i int
			set @i = 1

			--insert
			
			insert into Tipuri_Produse values ( @tipId,'Test tip')
			while @i <= @noRows
				begin 
					insert into Produse values (@prodId,'Test nume', 10, @tipId)
					set @prodId = @prodId + 1
					set @i = @i + 1
				end

			--select
			select * from Produse

			--update
			update Produse set nume_produs = 'Test nume updated' where produs_id > @idCopy
			select * from Produse
			--delete
			delete from Produse where produs_id > @idCopy
			delete from Tipuri_Produse where ID_tip = @tipId

		end
end
go

insert into Produse values (1,'Pizza', 30, 1);


create or alter procedure CRUDingrediente_produse --many to many
	@noRows int
as
begin
	declare @flag bit 
	declare @error varchar(32)

	exec CheckCRUD @noRows, @flag output, @error output

	if @flag = 1
		begin
			print 'Error: ' + @error
		end
	else
		begin
			set nocount on

			declare @tipId int
			select top 1 @tipId = ID_tip from Tipuri_Produse order by ID_tip desc
			set @tipId = @tipId + 1

			declare @prodId int
			select top 1 @prodId = produs_id from Produse order by produs_id desc
			declare @prodCopy int
			set @prodCopy = @prodId

			declare @ingId int
			select top 1 @ingId = ID_ingrediente from Ingrediente order by ID_ingrediente desc
			set @ingId = @ingId + 1
		

			--insert
			
			insert into ingrediente values (@ingId,'Test ingrediente')
			insert into Tipuri_Produse values (@tipId,'Test tip')
			--insert into Produse values (@prodId, 'Test nume' ,10, @tipId)
			
			declare @i int
			set @i = 1

			while @i <= @noRows
				begin
					set @prodId = @prodId + 1

					insert into Produse values (@prodId,'Test nume' ,10, @tipId)
					insert into Ingrediente_Produse values (@prodId, @ingId)

					set @i = @i + 1
				end
				
			--select
			select * from Ingrediente_Produse

			--update

			--delete
			delete from Ingrediente_Produse where ID_produs > @prodCopy
			delete from Produse where produs_id > @prodCopy
			delete from Tipuri_Produse where ID_tip = @tipId
			delete from Ingrediente where ID_ingrediente = @ingId
			
		end
end
go


insert into Ingrediente_Produse values (1,1)

-------------------------Views

---------View on produse and tipuri, ordered by tip

create or alter view ProduseTipuri
as
select top 100 pr.nume_produs as pName, Tipuri_Produse.tip as pTip
from Produse pr inner join Tipuri_Produse on pr.tip_produs = Tipuri_Produse.ID_tip
order by Tipuri_Produse.ID_tip desc;
go

if exists (select name from sys.indexes where name = 'N_INDEX_TIPURI_PRODUSE')
	drop index N_INDEX_TIPURI_PRODUSE on Tipuri_Produse
go
create nonclustered index N_INDEX_TIPURI_PRODUSE on Tipuri_Produse(tip)
go

select * from ProduseTipuri;
go

--------View on ingrediente, ordered by nume_ingrediente
create or alter view sortedIngrediente
as
select top 100 Ingrediente.nume_ingrediente
from Ingrediente 
order by nume_ingrediente desc;
go

if exists (select name from sys.indexes where name  = 'N_INDEX_INGREDIENTE')
	drop index N_INDEX_INGREDIENTE on Ingrediente
go
create nonclustered index N_INDEX_INGREDIENTE on Ingrediente(nume_ingrediente)
go



create or alter procedure test
as
begin

	exec CRUDingrediente 100
	exec CRUDproduse 100
	exec CRUDingrediente_produse 100

end
go

exec test
select * from Produse
select * from Tipuri_Produse
select * from Ingrediente
select * from Ingrediente_Produse


