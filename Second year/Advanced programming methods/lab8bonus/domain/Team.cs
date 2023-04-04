using System.Data.SqlClient;
using System.Data.SqlTypes;
using lab8bonus.domain.generic;

namespace lab8bonus.domain
{
    public class Team : Entity<int>
    {
        public string team_name { get; set; }

        public Team(SqlDataReader dataReader) : base((int) dataReader["id"])
        {
            team_name = (string) dataReader["team_name"];
        }
        
        public override string to_string()
        {
            string ret = "";
            ret += team_name;
            return ret;
        }
    }
}