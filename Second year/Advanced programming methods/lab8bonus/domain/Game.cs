using System;
using System.Data.SqlClient;
using lab8bonus.domain.generic;

namespace lab8bonus.domain
{
    public class Game : Entity<int>
    {
        public int id_team1 { get; set; }
        public int id_team2 { get; set; }
        public DateTime date { get; set; }

        public Game(SqlDataReader dataReader) : base((int)dataReader["id"])
        {
            id_team1 = (int) dataReader["id_team1"];
            id_team2 = (int) dataReader["id_team2"];
            date = (DateTime) dataReader["game_date"];
        }
        
        public override string to_string()
        {
            string ret = "";
            ret += date;
            return ret;
        }
    }
}