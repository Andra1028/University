using System.Collections.Generic;
using System.Data.SqlClient;
using lab8bonus.domain;
using lab8bonus.repository.generic;

namespace lab8bonus.repository
{
    public class GamesRepository : Repository<int, Game>
    {
        private SqlConnection connection;
        public GamesRepository(string connectionString)
        {
            connection = new SqlConnection(connectionString);
            connection.Open();
        }
        
        ~GamesRepository()
        {
            connection.Close();
        }

        public List<Game> findAll()
        {
            string sql = "select * from games";

            SqlCommand command = new SqlCommand(sql, connection);

            SqlDataReader dataReader = command.ExecuteReader();
            List<Game> games = new List<Game>();

            while (dataReader.Read())
            {
                Game newGame = new Game(dataReader);

                games.Add(newGame);
            }
            dataReader.Close();
            command.Dispose();
            
            return games;
        }
    }
}