using System.Collections.Generic;
using System.Data.SqlClient;
using lab8bonus.domain;
using lab8bonus.repository.generic;

namespace lab8bonus.repository
{
    public class TeamsRepository : Repository<int, Team>
    {
        private SqlConnection connection;
        
        public TeamsRepository(string connectionString)
        {
            
            connection = new SqlConnection(connectionString);
            connection.Open();
        }
        
        ~TeamsRepository()
        {
            connection.Close();
        }

        public List<Team> findAll()
        {
            string sql = "select * from teams";

            SqlCommand command = new SqlCommand(sql, connection);

            SqlDataReader dataReader = command.ExecuteReader();
            List<Team> teams = new List<Team>();

            while (dataReader.Read())
            {
                Team newTeam = new Team(dataReader);

                teams.Add(newTeam);
            }
            dataReader.Close();
            command.Dispose();
            
            return teams;
        }
    }
}