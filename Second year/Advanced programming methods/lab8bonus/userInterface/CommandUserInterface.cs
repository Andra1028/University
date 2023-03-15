using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using lab8bonus.domain;
using lab8bonus.repository;
using lab8bonus.service;

namespace lab8bonus.userInterface
{
    public class CommandUserInterface
    {

        private Service srv;
        public CommandUserInterface(Service newSrv)
        {
            srv = newSrv;
            
            Console.WriteLine("Welcome to NBA League Romania!");
            Console.WriteLine("0 for exit");
            Console.WriteLine("1 for seeing all players in a team");
            Console.WriteLine("2 for seeing all players in a team that played in a certain game");
            Console.WriteLine("3 for seeing the games in a certain period of time");
            Console.WriteLine("4 for seeing the result of a game");

            while(true)
            {
                Console.Write("your command: ");
                string command = Console.ReadLine();
                
                if (command == "0")
                {
                    break;
                }
                else if (command == "1")
                {
                    showPlayersInTeam();
                   
                }
                else if (command == "2")
                {
                    showPlayersInTeamInGame();
                }
                else if (command == "3")
                {
                    showGamesInInterval();
                }
                else if (command == "4")
                {
                    showGameScore();
                }
                else
                {
                    Console.Write("command incorrect!");
                }
            }
        }

        public void showPlayersInTeam()
        {
            
            // get user input
            Console.Write(" the name of the team: ");
            string teamName = Console.ReadLine();
            Console.WriteLine(teamName);
            
            // send it to service
            List<Player> players= srv.getPlayers(teamName);
            /*int id = srv.TeamName2Id(teamName);
            var myLinqQuery = from player in players
                where player.id_team==id
                select player;
    
// Query execution
            foreach(var name in myLinqQuery)
                Console.Write(name + " ");*/
            
            // if there were no players found, send a message and return 
            if (players.Count == 0)
            {
                Console.WriteLine(" there is no such team!");
                return;
            }

            // if there were players found, show them 
            foreach (Player player in players)
            {
               Console.WriteLine(player.to_string());
            }
        }

        public void showPlayersInTeamInGame()
        {
            // get user input
            Console.Write("the name of the team: ");
            string teamName = Console.ReadLine();
            Console.Write("the id of the game: ");
            string gameIdStr = Console.ReadLine();
            int gameId;
            
            // if parsing the input gameId fails, send a message and return 
            if (!Int32.TryParse(gameIdStr, out gameId))
            {
                Console.WriteLine(" there is no such team!");
                return;
            }

            // send it to service
            List<Player> players= srv.getPlayers(teamName, gameId);
            
            // if there were no players found, send a message and return 
            if (players.Count == 0)
            {
                Console.WriteLine("there is no such team!");
                return;
            }

            // if there were players found, show them 
            foreach (Player player in players)
            {
                Console.WriteLine(player.to_string());
            }
        }

        public void showGamesInInterval()
        {
            // get user input
            Console.Write(" the beginning date: ");
            string beginDateStr = Console.ReadLine();
            Console.Write("the ending date: ");
            string endDateStr = Console.ReadLine();
            int gameId;
            
            // parse the input
            DateTime beginDate = DateTime.Parse(beginDateStr);
            DateTime endDate = DateTime.Parse(endDateStr);
            
            // send them to service
            List<GameToShow> games = srv.getGames(beginDate, endDate);

            // if there were no games found, send a message and return 
            if (games.Count == 0)
            {
                Console.WriteLine(" there is not any game in that period!");
                return;
            }

            // if there were players found, show them 
            foreach (GameToShow game in games)
            {
                Console.WriteLine(game.to_string());
            }
        }

        public void showGameScore()
        {
            // get user input
            Console.Write("the id of the game: ");
            string gameIdStr = Console.ReadLine();
            int gameId;
            
            // if parsing the input gameId fails, send a message and return 
            if (!Int32.TryParse(gameIdStr, out gameId))
            {
                Console.WriteLine(" there is no such team!");
                return;
            }

            string teamName1, teamName2;
            int teamScore1, teamScore2;

            // send it to service
            srv.getGameScore(gameId,
                out teamName1, out teamScore1, out teamName2, out teamScore2);
            
            // show the result 
            Console.WriteLine(teamName1 + " scored " + teamScore1 + " points");
            Console.WriteLine(teamName2 + " scored " + teamScore2 + " points");

            if (teamScore1 > teamScore2)
            {
                Console.WriteLine(teamName1 + " won!");
            }
            else if (teamScore1 == teamScore2)
            {
                Console.WriteLine("There was a draw!");
            }
            else
            {
                Console.WriteLine(teamName2 + " won!");
            }
            
        }
    }
}