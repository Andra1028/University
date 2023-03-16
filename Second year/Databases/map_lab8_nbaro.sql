create database map_lab8_nbaro
go
use map_lab8_nbaro
go

create table teams(
id int primary key,
team_name varchar(200));
go

create table students(
id int primary key,
student_name varchar(200),
id_team int foreign key references teams(id),
id_school int);
go

select * from students


select * from students where id_team=1
select * from teams where team_name='Scoala Gimnaziala “Horea” -Houston Rockets'

create table games(
id int primary key,
id_team1 int foreign key references teams(id),
id_team2 int foreign key references teams(id),
game_date datetime);
go


drop table players_games
create table players_games(
--id int primary key,
id_player int foreign key references students(id),
id_game int foreign key references games(id),
constraint pk_pg primary key (id_player, id_game),
points int);
go

INSERT INTO teams (id, team_name) VALUES
(1, 'Scoala Gimnaziala “Horea” -Houston Rockets'), (2, 'Scoala Gimnaziala "Octavian Goga" -Los Angeles Lakers'), (3, 'Liceul Teoretic "Lucian Blaga" -LA Clippers'), (4, 'Scoala Gimnaziala "Ioan Bob" -Chicago Bulls'), (5, 'Scoala Gimnaziala "Ion Creanga" -Cleveland Cavaliers'),
(6, 'Colegiul National Pedagogic "Gheorghe Lazar" -Utah Jazz'), (7, 'Scoala Gimnaziala Internationala SPECTRUM -Brooklyn Nets'), (8, 'Colegiul National „Emil Racovita” -New Orleans Pelicans'), (9, 'Colegiul National "George Cosbuc" -Indiana Pacers'), (10, 'Scoala Gimnaziala "Ion Agarbiceanu" -Toronto Raptors'),
(11, 'Liceul Teoretic "Avram Iancu" -Charlotte Hornets'), (12, 'Scoala Gimnaziala "Constantin Brancusi" -Phoenix Suns'), (13, 'Liceul Teoretic "Onisifor Ghibu" -Portland TrailBlazers'), (14, 'Liceul Teoretic "Onisifor Ghibu" -Golden State Warriors'), (15, 'Liceul cu Program Sportiv Cluj-Napoca -Washington Wizards'),
(16, 'Liceul Teoretic "Nicolae Balcescu" -San Antonio Spurs'), (17, 'Liceul Teoretic "Gheorghe Sincai" -Orlando Magic'), (18, 'Scoala "Nicolae Titulescu" -Denver Nuggets'), (19, 'Scoala Gimnaziala "Liviu Rebreanu" -Detroit Pistons'), (20, 'Scoala Gimnaziala "Iuliu Hatieganu" -Atlanta Hawks'),
(21, 'Liceul Teoretic "Bathory Istvan" -Dallas Mavericks'), (22, 'Colegiul National "George Baritiu" -Sacramento Kings'), (23, 'Liceul Teoretic "Apaczai Csere Janos" -Oklahoma City Thunder'), (24, 'Seminarul Teologic Ortodox -Boston Celtics'), (25, 'Liceul de Informatica "Tiberiu Popoviciu" -New York Knicks'),
(26, 'Scoala Gimnaziala „Alexandru Vaida –Voevod" -Minnesota Timberwolves'), (27, 'Liceul Teoretic ELF -Miami Heat'), (28, 'Scoala Gimnaziala "Gheorghe Sincai" Floresti -Milwaukee Bucks');



create or alter procedure insertStudenti
as 
begin

DECLARE @i INT = 1
DECLARE @j INT = 1
DECLARE @teamId INT
DECLARE @schoolId INT

WHILE @i <= 30
BEGIN
  SET @teamId = @i
  SET @schoolId = @i
  WHILE @j <= 15
  BEGIN
    INSERT INTO students (id, student_name, id_team, id_school)
    VALUES (@j + (@i-1)*15, 'Student ' + CAST(@j AS VARCHAR(10)), @teamId, @schoolId)
    SET @j = @j + 1
  END
  SET @j = 1
  SET @i = @i + 1
end
end

exec insertStudenti

INSERT INTO games (id, id_team1, id_team2, game_date) VALUES
(1, 1, 2, '2022-01-01 12:00:00'),
(2, 3, 4, '2022-01-02 14:00:00'),
(3, 5, 6, '2022-01-03 16:00:00'),
(4, 7, 8, '2022-01-04 18:00:00'),
(5, 9, 10, '2022-01-05 20:00:00'),
(6, 11, 12, '2022-01-06 22:00:00'),
(7, 13, 14, '2022-01-07 00:00:00'),
(8, 15, 16, '2022-01-08 02:00:00'),
(9, 17, 18, '2022-01-09 04:00:00'),
(10, 19, 20, '2022-01-10 06:00:00');



create or alter procedure insertPlayersGames
as
begin
DECLARE @i INT = 1
DECLARE @playerId INT
DECLARE @gameId INT
DECLARE @points INT
DECLARE @team1 INT
DECLARE @team2 INT

WHILE @i <= (SELECT COUNT(*) FROM games)
BEGIN
  SELECT @gameId = id, @team1 = id_team1, @team2 = id_team2 FROM games WHERE id = @i
  INSERT INTO players_games (id_player, id_game, points)
  SELECT id, @gameId, ROUND((RAND() * 100), 0)
  FROM students
  WHERE id_team = @team1 OR id_team = @team2
  SET @i = @i + 1
END
end




delete from players_games
select * from players_games
select * from students
select * from teams

exec insertPlayersGames