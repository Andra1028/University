using System.Collections.Generic;
using System.Data.SqlClient;
using lab8bonus.domain;
using lab8bonus.repository.generic;

namespace lab8bonus.repository
{
    public class PlayersGamesRepository : Repository<int, PlayerGame>
    {
        private SqlConnection connection;
        
        public PlayersGamesRepository(string connectionString)
        {
            connection = new SqlConnection(connectionString);
            connection.Open();
        }

        ~PlayersGamesRepository()
        {
            connection.Close();
        }

        
        public List<PlayerGame> findAll()
        {
            string sql = "select pg.id_player as id_player," +
                         "pg.id_game as id_game," +
                         "pg.points as points," +
                         "st.id_team as id_team " +
                         "from players_games pg " +
                         "inner join students st on st.id = pg.id_player";

            SqlCommand command = new SqlCommand(sql, connection);

            SqlDataReader dataReader = command.ExecuteReader();
            List<PlayerGame> playersGames = new List<PlayerGame>();

            while (dataReader.Read())
            {
                PlayerGame newPlayerGame = new PlayerGame(dataReader);

                playersGames.Add(newPlayerGame);
            }
            dataReader.Close();
            command.Dispose();
            
            return playersGames;
        }
    }
}