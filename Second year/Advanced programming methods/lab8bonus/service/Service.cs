using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using lab8bonus.domain;
using lab8bonus.repository;
using lab8bonus.domain.generic;

namespace lab8bonus.service
{
    public class Service
    {
        
        TeamsRepository teamsRepo;
        PlayersRepository playersRepo;
        GamesRepository gamesRepo;
        PlayersGamesRepository playersGamesRepo;
        private SqlConnection connection;

        public Service(TeamsRepository TeamsRepo, PlayersRepository PlayersRepo,
            GamesRepository GamesRepo, PlayersGamesRepository PlayersGamesRepo)
        {
            teamsRepo = TeamsRepo;
            playersRepo = PlayersRepo;
            gamesRepo = GamesRepo;
            playersGamesRepo = PlayersGamesRepo;
            string connectionString = "Server=localhost; Database=lab8; Integrated Security=True;";
            connection = new SqlConnection(connectionString);
            connection.Open();
        }

        public int getTeamId(string name)
        {
            List<Team> teams = teamsRepo.findAll();
            foreach (Team t in teams)
            {
                if (t.team_name == name)
                {
                    return t.id;
                }
            }

            return -1;
        }

        public string getTeamName(int id)
        {
            List<Team> teams = teamsRepo.findAll();
            foreach (Team t in teams)
            {
                if (t.id == id)
                {
                    return t.team_name;
                }
            }

            return "";
        }

        private int getTeam1(int id)
        {
            List<Game> games = gamesRepo.findAll();
            foreach (Game g in games)
            {
                if (g.id == id)
                {
                    return g.id_team1;
                }
            }

            return -1;
        }

        private int getTeam2(int id)
        {
            List<Game> games = gamesRepo.findAll();
            foreach (Game g in games)
            {
                if (g.id == id)
                {
                    return g.id_team2;
                }
            }

            return -1;
        }

        public List<Player> getPlayersTeam(string name)
        {
            int teamId = getTeamId(name);
            List<Player> players = playersRepo.findAll();

            players.RemoveAll(player => player.id_team != teamId);

            return players;
        }

        public List<Player> getPlayersGame(string name, int game)
        {
            List<Player> players = getPlayersTeam(name);
            List<PlayerGame> playersGames = playersGamesRepo.findAll();

            List<int> Pid = new List<int>();

            foreach (Player player in players)
            {
                Pid.Add(player.id);
            }

            playersGames.RemoveAll(playerGame => playerGame.id_game != game ||
                                                 !Pid.Contains(playerGame.id_player));

            Pid = new List<int>();

            foreach (PlayerGame playergame in playersGames)
            {
                Pid.Add(playergame.id_player);
            }

            players.RemoveAll(player => !Pid.Contains(player.id));

            return players;
        }

        public List<Game> getGames(DateTime StartDate, DateTime EndDate)
        {
            List<Game> games = gamesRepo.findAll();

            games.RemoveAll(game => !(StartDate <= game.date && game.date <= EndDate));

            return games;
        }

        public void getScore(int id_game, out string team1, out int score1, out string team2, out int score2)
        {
            List<PlayerGame> playersGames = playersGamesRepo.findAll();

            playersGames.RemoveAll(gamePlayer => gamePlayer.id_game != id_game);

            if (playersGames.Count == 0)
            {
                team1 = team2 = "";
                score1 = score2 = -1;
            }

            int team1id = getTeam1(id_game);
            team1 = getTeamName(team1id);
            int team2id = getTeam2(id_game);
            team2 = getTeamName(team2id);

            score1 = score2 = 0;

            foreach (PlayerGame pg in playersGames)
            {
                if (pg.id_team == team1id)
                {
                    score1 += pg.points;
                }

                if (pg.id_team == team2id)
                {
                    score2 += pg.points;
                }
            }

        }
    }
}
