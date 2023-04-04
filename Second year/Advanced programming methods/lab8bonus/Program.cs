using System;
using System.Collections.Generic;
using System.Data.SqlClient;

using lab8bonus.domain;
using lab8bonus.repository;
using lab8bonus.service;


namespace lab8bonus
{
  internal class Program
  {
    public static void Main(string[] args)
    {
      string connectionString = "Server=localhost; Database=lab8; Integrated Security=True;";
      GamesRepository gamesRepo = new GamesRepository(connectionString);
      PlayersRepository playersRepo = new PlayersRepository(connectionString);
      PlayersGamesRepository playersGamesRepo = new PlayersGamesRepository(connectionString);
      TeamsRepository teamsRepo = new TeamsRepository(connectionString);

      Service srv = new Service(teamsRepo, playersRepo, gamesRepo, playersGamesRepo);
      
      while(true)
      {
        Console.WriteLine("1 Afiseazq toti jucatorii dintr-o echipa");
        Console.WriteLine("2 Afiseazq toti jucatorii activi ai unei echipa de la un anumit meci");
        Console.WriteLine("3 Afiseaza toate meciurile dintr-o anumita perioada calendaristica");
        Console.WriteLine("4 Afiseaza scorul unui meci");
        Console.WriteLine("0 Iesire");
        Console.Write("comanda: ");
        string command = Console.ReadLine();
                
        if (command == "0")
        {
          break;
        }
        else if (command == "1")
        {
          Console.Write(" Echipa: ");
          string teamName = Console.ReadLine();
          List<Player> players= srv.getPlayersTeam(teamName);
          if (players.Count == 0)
          {
            Console.WriteLine(" echipa nu exista ");
            break;
          }
          foreach (Player player in players)
          {
            Console.WriteLine(player.to_string());
          }
                   
        }
        else if (command == "2")
        {
          Console.Write("Echipa: ");
          string teamName = Console.ReadLine();
          Console.Write("Meciul: ");
          string gameIdStr = Console.ReadLine();
          int gameId;
            
         
          if (!Int32.TryParse(gameIdStr, out gameId))
          {
            Console.WriteLine(" meciul nu exista ");
            break;
          }
          
          List<Player> players= srv.getPlayersGame(teamName, gameId);
          if (players.Count == 0)
          {
            Console.WriteLine(" echipa nu exista ");
            break;
          }
          foreach (Player player in players)
          {
            Console.WriteLine(player.to_string());
          }
        }
        else if (command == "3")
        {
          Console.Write(" Inceput: ");
          string beginDateStr = Console.ReadLine();
          Console.Write(" Sfarsit: ");
          string endDateStr = Console.ReadLine();

          DateTime beginDate = DateTime.Parse(beginDateStr);
          DateTime endDate = DateTime.Parse(endDateStr);

          List<Game> games = srv.getGames(beginDate, endDate);

          if (games.Count == 0)
          {
            break;
          }

          foreach (Game game in games)
          {
            Console.WriteLine("echipa 1: " + srv.getTeamName(game.id_team1));
            Console.WriteLine("echipa 2: " + srv.getTeamName(game.id_team2));
            Console.WriteLine("data: " + game.date);
            
          }
        }
        else if (command == "4")
        {
          Console.Write(" Meciul: ");
          string gameIdStr = Console.ReadLine();
          int gameId;

          if (!Int32.TryParse(gameIdStr, out gameId))
          {
            Console.WriteLine(" meciul nu exista ");
            break;
          }

          string teamName1, teamName2;
          int teamScore1, teamScore2;

          srv.getScore(gameId,
            out teamName1, out teamScore1, out teamName2, out teamScore2);

          Console.WriteLine("echipa 1: " + teamName1+ " puncte: " + teamScore1);
          Console.WriteLine("echipa 2: " +teamName2 + " puncte:  " + teamScore2 );

        }
        else
        {
          Console.Write("comanda gresita");
        }
      }
    }

    }
  }
