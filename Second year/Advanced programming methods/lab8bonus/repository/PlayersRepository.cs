using System.Collections.Generic;
using System.Data.SqlClient;
using lab8bonus.domain;
using lab8bonus.repository.generic;

namespace lab8bonus.repository
{
    public class PlayersRepository : Repository<int, Player>
    {
        private SqlConnection connection;
        
        public PlayersRepository(string connectionString)
        {
            connection = new SqlConnection(connectionString);
            connection.Open();
        }

        ~PlayersRepository()
        {
            connection.Close();
        }

        public List<Player> findAll()
        {
            string sql = "select * from students";

            SqlCommand command = new SqlCommand(sql, connection);

            SqlDataReader dataReader = command.ExecuteReader();
            List<Player> players = new List<Player>();

            while (dataReader.Read())
            {
                Player newPlayer = new Player(dataReader);

                players.Add(newPlayer);
            }
            dataReader.Close();
            command.Dispose();
            
            return players;
        }
    }
}