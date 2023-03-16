use Restaurant
go

create table Versiune(versiuneNO int);

insert into Versiune values (0);


/*Modifica tipul coloanei an din tabela masini din int in year*/
CREATE or ALTER PROCEDURE do_proc_1
AS
BEGIN
    Alter table Masini
    Alter Column an float not null
    UPDATE Versiune
    Set versiuneNO = 1
    where versiuneNO = 0
END
GO

/*Adaugare constrangere de salariu sa fie cel putin 2000*/
CREATE OR ALTER PROCEDURE do_proc_2
AS
BEGIN
  
   ALTER TABLE Livratori
    Add constraint df_Salariu
    Default 2000 for salariu
    UPDATE Versiune
    Set versiuneNO = 2
    where versiuneNO = 1
END
GO

/*Creare tabela pentru review*/
CREATE OR ALTER PROCEDURE do_proc_3
AS
BEGIN
    Create Table Review(
        ID_Review int Not Null Primary Key identity,
        descriere varchar(50) Not Null,
    );
    UPDATE Versiune
    Set versiuneNO = 3
    where versiuneNO = 2
END
GO

/*Adaugare coloana de nota in tabela review*/
CREATE OR ALTER PROCEDURE do_proc_4
AS
BEGIN
    Alter Table Review
    Add Nota int not null
    UPDATE Versiune
    Set versiuneNO = 4
    where versiuneNO = 3
END
GO


/*review urile au un id de client*/
CREATE OR ALTER PROCEDURE do_proc_5
AS
BEGIN
    Alter table Review
    Add constraint fk_client_review foreign key(ID_Review) References Comenzi(ID_comanda)
    UPDATE Versiune
    Set versiuneNO = 5
    where versiuneNO = 4
END
GO


CREATE or ALTER PROCEDURE undo_proc_1
AS
BEGIN
    Alter table Masini
    Alter Column an int
    UPDATE Versiune
    Set versiuneNO = 0
    where versiuneNO = 1
END
GO

CREATE OR ALTER PROCEDURE undo_proc_2
AS
BEGIN
    ALTER TABLE Livratori
    Drop constraint df_Salariu;
    UPDATE Versiune
    Set versiuneNO = 1
    where versiuneNO = 2
END
GO

CREATE OR ALTER PROCEDURE undo_proc_3
AS
BEGIN
    Drop Table Review
    UPDATE Versiune
    Set versiuneNO = 2
    where versiuneNO = 3
END
GO

CREATE OR ALTER PROCEDURE undo_proc_4
AS
BEGIN
    Alter table Review
    Drop Column Nota
    UPDATE Versiune
    Set versiuneNO = 3
    where versiuneNO = 4
END
GO

CREATE OR ALTER PROCEDURE undo_proc_5
AS
BEGIN
    Alter table Review
    Drop constraint fk_client_review
    UPDATE Versiune
    Set versiuneNO = 4
    where versiuneNO = 5
END
GO


CREATE OR ALTER PROCEDURE Main @vers int
AS
BEGIN
    DECLARE @versiuneNO int
    SET @versiuneNO = (Select * From Versiune)
    Print('Versiunea curenta a tabelei')
    Print(@versiuneNO)
    IF(@versiuneNO = @vers)
        print('Versiunea este aceeasi')
    ELSE IF(@vers > 5 OR @vers < 0)
        print('Versiunea primita nu poate fi efectuata')
    ELSE IF(@versiuneNO < @vers)
        BEGIN
        Print('Se face Update la tabela')
        if(@versiuneNO = 0 AND @versiuneNO != @vers)
            Begin
            EXEC do_proc_1
            End
        SET @versiuneNO = (Select * From Versiune)



       if(@versiuneNO = 1 AND @versiuneNO != @vers)
            Begin
            EXEC do_proc_2
            End
        SET @versiuneNO = (Select * From Versiune)



       if(@versiuneNO = 2 AND @versiuneNO != @vers)
            Begin
            EXEC do_proc_3
            End
        SET @versiuneNO = (Select * From Versiune)



       if(@versiuneNO = 3 AND @versiuneNO != @vers)
            Begin
            EXEC do_proc_4
            End
        SET @versiuneNO = (Select * From Versiune)



       if(@versiuneNO = 4 AND @versiuneNO != @vers)
            Begin
            EXEC do_proc_5
            End
        SET @versiuneNO = (Select * From Versiune)



       END
    ELSE
        BEGIN
        Print('Se face Undo la tabela')
        if(@versiuneNO = 5 AND @versiuneNO != @vers)
            Begin
            EXEC undo_proc_5
            End
        SET @versiuneNO = (Select * From Versiune)



       if(@versiuneNO = 4 AND @versiuneNO != @vers)
            Begin
            EXEC undo_proc_4
            End
        SET @versiuneNO = (Select * From Versiune)



       if(@versiuneNO = 3 AND @versiuneNO != @vers)
            Begin
            EXEC undo_proc_3
            End
        SET @versiuneNO = (Select * From Versiune)



       if(@versiuneNO = 2 AND @versiuneNO != @vers)
            Begin
            EXEC undo_proc_2
            End
        SET @versiuneNO = (Select * From Versiune)



       if(@versiuneNO = 1 AND @versiuneNO != @vers)
            Begin
            EXEC undo_proc_1
            End
        SET @versiuneNO = (Select * From Versiune)
        END



   Print('Versiunea dupa modificare a tabelei')
    Print(@versiuneNO)
END
GO


EXEC Main 0

select * from Masini
/*EXEC do_proc_1
EXEC do_proc_2
EXEC do_proc_3
EXEC do_proc_4
EXEC do_proc_5


EXEC undo_proc_1
EXEC undo_proc_2
EXEC undo_proc_3
EXEC undo_proc_4
EXEC undo_proc_5*/